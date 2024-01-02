package com.tutorcenter.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.question.AnswerResDto;
import com.tutorcenter.dto.question.QuestionResDto;
import com.tutorcenter.model.Answer;
import com.tutorcenter.model.Question;
import com.tutorcenter.model.Subject;
import com.tutorcenter.service.SubjectService;
import com.tutorcenter.service.SystemVariableService;
import com.tutorcenter.service.TestService;

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

    // @GetMapping("/")
    // public ApiResponseDto<List<QuestionDto>>

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
}
