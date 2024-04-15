package com.wiley.DuoFinance.model;

public class Answer {

    private Integer questionId;
    private String answer;

    public Answer() {
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
