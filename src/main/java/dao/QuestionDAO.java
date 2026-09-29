package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;
import com.onlineexam.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    // =========================
    // ADD QUESTION
    // =========================

    public boolean addQuestion(
            int examId,
            String questionText,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String correctOption) {

        String sql =
                "INSERT INTO questions " +
                        "(exam_id, question_text, option_a, option_b, " +
                        "option_c, option_d, correct_option) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, examId);
            statement.setString(2, questionText);
            statement.setString(3, optionA);
            statement.setString(4, optionB);
            statement.setString(5, optionC);
            statement.setString(6, optionD);
            statement.setString(7, correctOption);

            statement.executeUpdate();

            updateQuestionCount(examId);

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add question error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // GET QUESTIONS
    // =========================

    public List<Question> getQuestionsByExam(
            int examId) {

        List<Question> questions =
                new ArrayList<>();

        String sql =
                "SELECT * FROM questions " +
                        "WHERE exam_id = ? " +
                        "ORDER BY id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, examId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Question question =
                        new Question();

                question.setId(
                        result.getInt("id")
                );

                question.setExamId(
                        result.getInt("exam_id")
                );

                question.setQuestionText(
                        result.getString(
                                "question_text"
                        )
                );

                question.setOptionA(
                        result.getString("option_a")
                );

                question.setOptionB(
                        result.getString("option_b")
                );

                question.setOptionC(
                        result.getString("option_c")
                );

                question.setOptionD(
                        result.getString("option_d")
                );

                question.setCorrectOption(
                        result.getString(
                                "correct_option"
                        )
                );

                questions.add(question);
            }

        } catch (Exception e) {

            System.out.println(
                    "Get questions error: "
                            + e.getMessage()
            );
        }

        return questions;
    }


    // =========================
    // UPDATE QUESTION
    // =========================

    public boolean updateQuestion(
            int id,
            String questionText,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String correctOption) {

        String sql =
                "UPDATE questions SET " +
                        "question_text = ?, " +
                        "option_a = ?, " +
                        "option_b = ?, " +
                        "option_c = ?, " +
                        "option_d = ?, " +
                        "correct_option = ? " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, questionText);
            statement.setString(2, optionA);
            statement.setString(3, optionB);
            statement.setString(4, optionC);
            statement.setString(5, optionD);
            statement.setString(6, correctOption);
            statement.setInt(7, id);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Update question error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // DELETE QUESTION
    // =========================

    public boolean deleteQuestion(
            int id,
            int examId) {

        String sql =
                "DELETE FROM questions " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

            updateQuestionCount(examId);

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Delete question error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // UPDATE QUESTION COUNT
    // =========================

    private void updateQuestionCount(
            int examId) {

        String sql =
                "UPDATE exams " +
                        "SET total_questions = " +
                        "(SELECT COUNT(*) FROM questions " +
                        "WHERE exam_id = ?) " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, examId);
            statement.setInt(2, examId);

            statement.executeUpdate();

        } catch (Exception e) {

            System.out.println(
                    "Question count update error: "
                            + e.getMessage()
            );
        }
    }
}
