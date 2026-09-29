package com.onlineexam.model;

public class Exam {

    private int id;
    private String title;
    private String subject;
    private int durationMinutes;
    private int totalQuestions;

    public Exam() {
    }

    public Exam(int id, String title, String subject,
                int durationMinutes, int totalQuestions) {

        this.id = id;
        this.title = title;
        this.subject = subject;
        this.durationMinutes = durationMinutes;
        this.totalQuestions = totalQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}