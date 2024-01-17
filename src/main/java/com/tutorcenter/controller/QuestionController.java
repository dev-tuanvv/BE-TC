package com.tutorcenter.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.question.AnswerManagerResDto;
import com.tutorcenter.dto.question.AnswerResDto;
import com.tutorcenter.dto.question.QuestionDetailManagerResDto;
import com.tutorcenter.dto.question.QuestionManagerResDto;
import com.tutorcenter.dto.question.QuestionResDto;
import com.tutorcenter.dto.question.SubmitReqDto;
import com.tutorcenter.model.Answer;
import com.tutorcenter.model.Question;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorSubject;
import com.tutorcenter.service.SubjectService;
import com.tutorcenter.service.SystemVariableService;
import com.tutorcenter.service.TestService;
import com.tutorcenter.service.TutorService;
import com.tutorcenter.service.TutorSubjectService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/imported/";

    @Autowired
    private TestService testService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SystemVariableService systemVariableService;
    @Autowired
    private TutorService tutorService;
    @Autowired
    private TutorSubjectService tutorSubjectService;
    // @GetMapping("/")
    // public ApiResponseDto<List<QuestionDto>>

    @GetMapping("/")
    public ApiResponseDto<List<QuestionManagerResDto>> getAllQuestions() {

        try {
            List<QuestionManagerResDto> response = new ArrayList<>();
            List<Question> questions = testService.getAllQuestion();
            for (Question q : questions) {
                QuestionManagerResDto dto = new QuestionManagerResDto();
                dto.fromQuestion(q);
                response.add(dto);
            }
            return ApiResponseDto.<List<QuestionManagerResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<QuestionManagerResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ApiResponseDto<QuestionDetailManagerResDto> getQuestionById(@PathVariable int id) {
        try {
            QuestionDetailManagerResDto response = new QuestionDetailManagerResDto();
            Question question = testService.getQuestionById(id);
            response.fromQuestion(question);
            List<Answer> answers = testService.getAllAnswersByQId(question);
            List<AnswerManagerResDto> answerResDtos = new ArrayList<>();
            for (Answer a : answers) {
                AnswerManagerResDto answerResDto = new AnswerManagerResDto();
                answerResDto.fromAnswer(a);
                answerResDtos.add(answerResDto);
            }
            response.setAnswers(answerResDtos);

            return ApiResponseDto.<QuestionDetailManagerResDto>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<QuestionDetailManagerResDto>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/update")
    public ApiResponseDto<String> updateQuestion(@RequestBody QuestionDetailManagerResDto dto) {
        try {
            Question question = new Question();
            dto.toQuestion(question);
            List<AnswerManagerResDto> answerResDtos = dto.getAnswers();
            for (AnswerManagerResDto a : answerResDtos) {
                Answer answer = new Answer();
                a.toAnswer(answer);
                answer.setQuestion(question);
                testService.saveAnswer(answer);
            }
            question.setSubject(subjectService.getSubjectById(dto.getSubjectId()).orElse(null));
            testService.saveQuestion(question);

            return ApiResponseDto.<String>builder().data("Update question success").build();
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponseDto<String> deleteQuestion(@PathVariable int id) {
        try {
            testService.deleteQuestion(id);

            return ApiResponseDto.<String>builder().data("Delete question success").build();
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/import")
    public ApiResponseDto<String> importFromExcelToDB(@RequestParam("file") MultipartFile file) {
        Path pathFolder = Paths.get(UPLOAD_DIRECTORY);

        try {
            if (!Files.exists(pathFolder)) {
                Files.createDirectories(pathFolder);
            }
            String filePath = pathFolder.resolve(UUID.randomUUID() + file.getOriginalFilename()) + "";

            file.transferTo(new File(filePath));
            testService.readExcelFile(filePath);
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<String>builder().data("Data imported successfully").build();
    }

    @PostMapping("/importV2")
    public ResponseEntity<byte[]> importFromExcelToDBV2(@RequestParam("file") MultipartFile file) {
        Path pathFolder = Paths.get(UPLOAD_DIRECTORY);

        try {
            if (!Files.exists(pathFolder)) {
                Files.createDirectories(pathFolder);
            }
            String filePath = pathFolder.resolve(UUID.randomUUID() + file.getOriginalFilename()) + "";

            file.transferTo(new File(filePath));
            byte[] rs = testService.readExcelFileV2(filePath);
            return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "rs.xlsx")
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok()
                    .body(null);
        }
    }

    @GetMapping("/exam/{sId}")
    public ApiResponseDto<List<QuestionResDto>> getExamBySubjectId(@PathVariable int sId) {
        List<QuestionResDto> response = new ArrayList<>();
        try {
            Subject subject = subjectService.getSubjectById(sId).orElse(null);
            List<Question> questions3 = testService.getRandQuestion(subject, 3,
                    Integer.parseInt(systemVariableService.getSysVarByVarKey("no_questions_3").getValue()));
            List<Question> questions2 = testService.getRandQuestion(subject, 2,
                    Integer.parseInt(systemVariableService.getSysVarByVarKey("no_questions_2").getValue()));
            List<Question> questions1 = testService.getRandQuestion(subject, 1,
                    Integer.parseInt(systemVariableService.getSysVarByVarKey("no_questions_1").getValue()));
            List<Question> questions = questions1;
            questions.addAll(questions2);
            questions.addAll(questions3);
            for (Question q : questions) {
                QuestionResDto dto = new QuestionResDto();
                dto.fromQuestion(q);
                List<Answer> answers = testService.getAllAnswersByQId(q);
                List<AnswerResDto> answerResDtos = new ArrayList<>();
                for (Answer a : answers) {
                    AnswerResDto answerResDto = new AnswerResDto();
                    answerResDto.fromAnswer(a);
                    answerResDtos.add(answerResDto);
                }
                Collections.shuffle(answerResDtos);
                dto.setAnswers(answerResDtos);
                response.add(dto);
            }
            Collections.shuffle(response);
        } catch (Exception e) {
            return ApiResponseDto.<List<QuestionResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<QuestionResDto>>builder().data(response).build();
    }

    @PutMapping("/submit")
    public ApiResponseDto<Integer> submit(@RequestBody List<SubmitReqDto> submitReqDtos) {
        int result = 0;
        int maxGrade = 0;
        int grade = 0;
        try {
            // check tutor id
            Tutor tutor = tutorService.getTutorById(Common.getCurrentUserId()).orElse(null);
            if (tutor == null) {
                return ApiResponseDto.<Integer>builder().responseCode("500")
                        .message("Bạn chưa đủ điều kiện làm bài test này")
                        .build();
            }
            Subject subject = subjectService.getSubjectById(submitReqDtos.get(0).getSubjectId()).orElse(null);
            // check correct & get grade

            for (SubmitReqDto submitReqDto : submitReqDtos) {
                if (submitReqDto.getAnswerId() != 0) {
                    grade += testService.checkAnswer(submitReqDto.getAnswerId());
                }
                maxGrade += testService.getDifficulty(submitReqDto.getQuestionId());
            }
            // get test result
            result = (int) (((double) grade / maxGrade) * 100);

            // tạo tutorSubject
            TutorSubject tutorSubject = tutorSubjectService.getTutorSubjectBySubjectAndTutor(subject, tutor);

            if (tutorSubject == null) {
                tutorSubject = new TutorSubject();
                tutorSubject.setSubject(subject);
                tutorSubject.setTimes(1);
                tutorSubject.setTutor(tutor);
            } else {
                tutorSubject.setTimes(tutorSubject.getTimes() + 1);
            }
            tutorSubject.setLatestGrade(result);
            tutorSubject.setLatestDate(new Date(System.currentTimeMillis()));
            tutorSubjectService.save(tutorSubject);
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<Integer>builder().data(result).build();
    }
}
