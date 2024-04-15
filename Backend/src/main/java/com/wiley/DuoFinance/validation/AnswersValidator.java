package com.wiley.DuoFinance.validation;

import com.wiley.DuoFinance.model.Answer;
import com.wiley.DuoFinance.model.Question;
import com.wiley.DuoFinance.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AnswersValidator {

    private static Map<String, String> errors;

    public static boolean isValidAnswers(List<Answer> answers) {

        errors = new HashMap<>();

        validateNumberOfAnswers(answers);
        validateAnswersFormat(answers);

        return errors.size() == 0;
    }

    public static Map<String, String> getErrors() {
        return errors;
    }

    private static void validateNumberOfAnswers(List<Answer> answers) {

        if(answers == null) {
            errors.put("number", "You need to send 3 answers.");
            return;
        }

        if(answers.size() != 3) {
            errors.put("number", "You need to send 3 answers.");
        }
    }

    private static void validateAnswersFormat(List<Answer> answers) {

        boolean isValid = true;

        for(Answer answer : answers) {
            if(answer.getQuestionId() == null || answer.getAnswer() == null) {
                isValid = false;
            }
        }

        if(!isValid) {
            errors.put("answers", "You need to submit a question Id and an answer.");
        }
    }

}
