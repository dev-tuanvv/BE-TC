package com.tutorcenter.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Answer;
import com.tutorcenter.model.Question;
import com.tutorcenter.model.Subject;
import com.tutorcenter.repository.AnswerRepository;
import com.tutorcenter.repository.QuestionRepository;
import com.tutorcenter.repository.SubjectRepository;
import com.tutorcenter.service.TestService;

@Component
public class TestServiceImpl implements TestService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getAllQuestionBySId(Subject subject) {
        return questionRepository.findBySubject(subject);
    }

    @Override
    public List<Answer> getAllAnswersByQId(Question question) {
        return answerRepository.findByQuestion(question);
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Answer saveAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public boolean readExcelFile(String filePath) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)))) {
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }
                // get question data (0: subjectId, 1: difficulty, 2: content)
                Question question = new Question();
                question.setSubject(
                        subjectRepository.findById((int) row.getCell(0).getNumericCellValue()).orElse(null));
                question.setDifficulty((int) row.getCell(1).getNumericCellValue());
                question.setContent(row.getCell(2).getStringCellValue());
                question.setDateCreate(new Date(System.currentTimeMillis()));
                Question q = questionRepository.save(question);

                // get answers list data (3: correct answer, >4: other answers)
                List<Answer> answerList = new ArrayList<>();
                for (int i = 3; i < row.getPhysicalNumberOfCells(); i++) {
                    Answer answer = new Answer();
                    answer.setQuestion(q);
                    if (row.getCell(i).getCellType() == CellType.STRING) {
                        answer.setContent(row.getCell(i).getStringCellValue());
                    } else if (row.getCell(i).getCellType() == CellType.NUMERIC) {
                        answer.setContent(String.valueOf(row.getCell(i).getNumericCellValue()).replaceAll("\\.0$", ""));
                    }

                    if (i == 3) {
                        answer.setCorrect(true);
                    } else {
                        answer.setCorrect(false);
                    }
                    answerList.add(answer);
                }
                answerRepository.saveAll(answerList);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public byte[] readExcelFileV2(String filePath) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)))) {
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.index);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    Cell rs = row.createCell(7);
                    rs.setCellValue("Result");
                    continue; // Skip header row
                }
                String subjectId = formatter.formatCellValue(row.getCell(0));
                String difficult = formatter.formatCellValue(row.getCell(1));
                String questionContent = formatter.formatCellValue(row.getCell(2));
                String correctAnswer = formatter.formatCellValue(row.getCell(3));
                String wrongAnswer1 = formatter.formatCellValue(row.getCell(4));
                String wrongAnswer2 = formatter.formatCellValue(row.getCell(5));
                String wrongAnswer3 = formatter.formatCellValue(row.getCell(6));
                if (StringUtils.isEmpty(subjectId) &&
                        StringUtils.isEmpty(difficult) &&
                        StringUtils.isEmpty(questionContent) &&
                        StringUtils.isEmpty(correctAnswer) &&
                        StringUtils.isEmpty(wrongAnswer1) &&
                        StringUtils.isEmpty(wrongAnswer2) &&
                        StringUtils.isEmpty(wrongAnswer3)) {
                    break;
                }
                Cell rs = row.createCell(7);
                // get question data (0: subjectId, 1: difficulty, 2: content)
                int sjId = -1;
                try {
                    sjId = Integer.parseInt(subjectId);
                } catch (Exception e) {
                    rs.setCellValue("Invalid subjectId type");
                    style.setFillForegroundColor(IndexedColors.RED.index);
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    rs.setCellStyle(style);
                    continue;
                }
                int difficultInt = -1;
                try {
                    difficultInt = Integer.parseInt(difficult);
                    if (difficultInt < 1 || difficultInt > 3) {
                        rs.setCellValue("Out of range difficult");
                        style.setFillForegroundColor(IndexedColors.RED.index);
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        rs.setCellStyle(style);
                        continue;
                    }
                } catch (Exception e) {
                    rs.setCellValue("Invalid difficulty type");
                    style.setFillForegroundColor(IndexedColors.RED.index);
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    rs.setCellStyle(style);
                    continue;
                }

                Subject subject = subjectRepository.findById(sjId).orElse(null);
                if (subject == null) {
                    rs.setCellValue("Wrong subject Id");
                    style.setFillForegroundColor(IndexedColors.RED.index);
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    rs.setCellStyle(style);
                    continue;
                }
                List<String> questionContentList = questionRepository.findAll().stream().map(q -> q.getContent())
                        .toList();
                if (questionContentList.contains(questionContent)) {
                    style.setFillForegroundColor(IndexedColors.RED.index);
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    rs.setCellValue("Duplicated question");
                    rs.setCellStyle(style);
                    continue;
                }
                Question question = new Question();
                question.setSubject(subject);
                question.setDifficulty(difficultInt);
                question.setDateCreate(new Date(System.currentTimeMillis()));
                questionRepository.save(question);

                List<Answer> answerList = new ArrayList<>();

                String[] answerContents = { correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3 };

                for (int i = 0; i < answerContents.length; i++) {
                    Answer answer = new Answer();
                    answer.setQuestion(question);
                    answer.setContent(answerContents[i]);
                    answer.setCorrect(i == 0);
                    answerList.add(answer);
                }
                answerRepository.saveAll(answerList);
                style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                rs.setCellValue("Success");
                rs.setCellStyle(style);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] data = outputStream.toByteArray();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Question> getRandQuestion(Subject subject, int difficulty, int no) {

        List<Question> questions = questionRepository.findBySubjectAndDifficulty(subject, difficulty);

        if (questions.size() < no) {
            int remainingQuestions = no - questions.size();
            // nếu ko đủ câu hỏi có difficulty thì lấy câu hỏi có difficulty -1
            List<Question> remain = new ArrayList<>();
            if (difficulty > 1) {
                remain = getRandQuestion(subject, difficulty - 1, remainingQuestions);
            } else {
                remain = getRandQuestion(subject, difficulty, remainingQuestions);
            }
            questions.addAll(0, remain);
        }
        if (questions.size() > no) {
            List<Question> excess = questions.subList(0, no);
            questions = excess;
        }
        Collections.shuffle(questions);
        return questions;
    }

    @Override
    public int checkAnswer(int answerId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        if (answer.isCorrect())
            return answer.getQuestion().getDifficulty();
        else
            return 0;
    }

    @Override
    public int getDifficulty(int qId) {
        return questionRepository.findById(qId).orElse(null).getDifficulty();
    }

    @Override
    public void deleteQuestion(int qId) {
        Question question = getQuestionById(qId);
        for (Answer a : getAllAnswersByQId(question)) {
            answerRepository.delete(a);
        }
        questionRepository.delete(question);
    }

    @Override
    public Question getQuestionById(int qId) {
        return questionRepository.findById(qId).orElse(null);
    }

}
