package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;
import com.onlineexam.model.Exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    // =========================
    // ADD EXAM
    // =========================

    public boolean addExam(
            String title,
            String subject,
            int durationMinutes) {

        String sql =
                "INSERT INTO exams " +
                        "(title, subject, duration_minutes, total_questions) " +
                        "VALUES (?, ?, ?, 0)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, title);
            statement.setString(2, subject);
            statement.setInt(3, durationMinutes);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add exam error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // GET ALL EXAMS
    // =========================

    public List<Exam> getAllExams() {

        List<Exam> exams =
                new ArrayList<>();

        String sql =
                "SELECT * FROM exams ORDER BY id DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            while (result.next()) {

                Exam exam =
                        new Exam();

                exam.setId(
                        result.getInt("id")
                );

                exam.setTitle(
                        result.getString("title")
                );

                exam.setSubject(
                        result.getString("subject")
                );

                exam.setDurationMinutes(
                        result.getInt(
                                "duration_minutes"
                        )
                );

                exam.setTotalQuestions(
                        result.getInt(
                                "total_questions"
                        )
                );

                exams.add(exam);
            }

        } catch (Exception e) {

            System.out.println(
                    "Get exams error: "
                            + e.getMessage()
            );
        }

        return exams;
    }


    // =========================
    // UPDATE EXAM
    // =========================

    public boolean updateExam(
            int id,
            String title,
            String subject,
            int durationMinutes) {

        String sql =
                "UPDATE exams " +
                        "SET title = ?, " +
                        "subject = ?, " +
                        "duration_minutes = ? " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, title);
            statement.setString(2, subject);
            statement.setInt(3, durationMinutes);
            statement.setInt(4, id);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Update exam error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // DELETE EXAM
    // =========================

    public boolean deleteExam(int id) {

        String sql =
                "DELETE FROM exams WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Delete exam error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}