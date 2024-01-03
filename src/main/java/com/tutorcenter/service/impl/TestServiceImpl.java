package com.tutorcenter.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<Question> getRandQuestion(Subject subject, int difficulty, int no) {

        List<Question> questions = questionRepository.findBySubjectAndDifficulty(subject, difficulty);

        if (questions.size() < no) {
            int remainingQuestions = no - questions.size();
            List<Question> remain = getRandQuestion(subject, difficulty - 1, remainingQuestions);
            questions.addAll(0, remain);
        }

        return questions;
    }

}
