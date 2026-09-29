package com.onlineexam.model;

public class Result {

    private int id;
    private int userId;
    private int examId;
    private int score;
    private int totalQuestions;
    private double percentage;

    public Result() {
    }

    public Result(
            int id,
            int userId,
            int examId,
            int score,
            int totalQuestions,
            double percentage) {

        this.id = id;
        this.userId = userId;
        this.examId = examId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}