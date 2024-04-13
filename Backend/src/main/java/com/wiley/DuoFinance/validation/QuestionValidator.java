package com.wiley.DuoFinance.validation;

import com.wiley.DuoFinance.model.Question;

import java.util.HashMap;
import java.util.Map;

public class QuestionValidator {

    private static Map<String, String> errors;

    public static boolean isValidQuestion(Question question) {

        errors = new HashMap<>();

        validateQuestionText(question.getQuestionText());
        validateChoice(question.getChoiceA(), "choiceA");
        validateChoice(question.getChoiceB(), "choiceB");
        validateChoice(question.getChoiceC(), "choiceC");
        validateChoice(question.getAnswer(), "answer");
        validateAnswerMatch(question);

        return errors.size() == 0;
    }

    public static Map<String, String> getErrors() {
        return errors;
    }

    private static void validateQuestionText(String questionText) {

        boolean isValid = true;
        String message = "The question text must be between 5 and 2000 characters.";

        if(questionText == null) {
            isValid = false;
        } else {
            if(questionText.isBlank()) isValid = false;
            if(questionText.isEmpty()) isValid = false;
            if(questionText.length() < 5 || questionText.length() > 2000) isValid = false;
        }

        if(!isValid) {
            errors.put("questionText", message);
        }
    }

    private static void validateChoice(String choice, String textInput) {

        boolean isValid = true;
        String message = "The " + textInput + " text must be between 5 and 2000 characters.";

        if(choice == null) {
            isValid = false;
        } else {
            if(choice.isBlank()) isValid = false;
            if(choice.isEmpty()) isValid = false;
            if(choice.length() < 5 || choice.length() > 2000) isValid = false;
        }

        if(!isValid) {
            errors.put(textInput, message);
        }
    }

    private static void validateAnswerMatch(Question question) {

        boolean isValid = true;
        String message = "The answer must correspond to one of the choice.";

        if(question.getQuestionText() != null &&
                question.getChoiceA() != null &&
                question.getChoiceB() != null &&
                question.getChoiceC() != null &&
                question.getAnswer() != null) {

            if(!question.getAnswer().equals(question.getChoiceA()) &&
                    !question.getAnswer().equals(question.getChoiceB()) &&
                    !question.getAnswer().equals(question.getChoiceC())) {

                isValid = false;
            }
        }

        if(!isValid) {
            errors.put("match", message);
        }
    }
}
