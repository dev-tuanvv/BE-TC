package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Answer;
import com.tutorcenter.model.Question;
import com.tutorcenter.model.Subject;

@Service
public interface TestService {
    List<Question> getAllQuestion();

    List<Question> getAllQuestionBySId(Subject subject);

    List<Answer> getAllAnswersByQId(Question question);

    Question saveQuestion(Question question);

    Answer saveAnswer(Answer answer);

    boolean readExcelFile(String filePath);

    List<Question> getRandQuestion(Subject subject, int difficulty, int no);
}
