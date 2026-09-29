package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AnswerDAO {

    /**
     * Saves one answer given by a student.
     *
     * @param resultId result associated with the exam
     * @param questionId question answered
     * @param selectedOption option selected by student
     * @param isCorrect whether the selected answer is correct
     */
    public List<Object[]> getAnswersByResult(int resultId) {

        List<Object[]> answers =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "a.question_id, " +
                        "q.question_text, " +
                        "q.option_a, " +
                        "q.option_b, " +
                        "q.option_c, " +
                        "q.option_d, " +
                        "a.selected_option, " +
                        "q.correct_option, " +
                        "a.is_correct " +
                        "FROM answers a " +
                        "JOIN questions q " +
                        "ON a.question_id = q.id " +
                        "WHERE a.result_id = ? " +
                        "ORDER BY a.question_id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    resultId
            );

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                answers.add(
                        new Object[]{
                                result.getInt("question_id"),
                                result.getString("question_text"),
                                result.getString("option_a"),
                                result.getString("option_b"),
                                result.getString("option_c"),
                                result.getString("option_d"),
                                result.getString("selected_option"),
                                result.getString("correct_option"),
                                result.getBoolean("is_correct")
                        }
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Get answers error: "
                            + e.getMessage()
            );
        }

        return answers;
    }

    public boolean saveAnswer(
            int resultId,
            int questionId,
            String selectedOption,
            boolean isCorrect) {

        String sql =
                "INSERT INTO answers " +
                        "(result_id, question_id, selected_option, is_correct) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, resultId);
            statement.setInt(2, questionId);
            statement.setString(3, selectedOption);
            statement.setBoolean(4, isCorrect);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Save answer error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}