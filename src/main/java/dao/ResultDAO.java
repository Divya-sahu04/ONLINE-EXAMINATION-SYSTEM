package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;
import com.onlineexam.model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;




public class ResultDAO {

    /**
     * Saves a student's exam result.
     *
     * @return generated result ID
     *         or -1 if saving fails
     */
    public List<Object[]> getAllResults() {

        List<Object[]> results =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "r.id, " +
                        "u.name, " +
                        "u.email, " +
                        "e.title, " +
                        "e.subject, " +
                        "r.score, " +
                        "r.total_questions, " +
                        "r.percentage, " +
                        "r.completed_at " +
                        "FROM results r " +
                        "JOIN users u " +
                        "ON r.user_id = u.id " +
                        "JOIN exams e " +
                        "ON r.exam_id = e.id " +
                        "ORDER BY r.completed_at DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                results.add(
                        new Object[]{
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("email"),
                                result.getString("title"),
                                result.getString("subject"),
                                result.getInt("score"),
                                result.getInt("total_questions"),
                                result.getDouble("percentage"),
                                result.getTimestamp("completed_at")
                        }
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Get all results error: "
                            + e.getMessage()
            );
        }

        return results;
    }

    public int createResult(
            int userId,
            int examId,
            int score,
            int totalQuestions,
            double percentage) {

        String sql =
                "INSERT INTO results " +
                        "(user_id, exam_id, score, total_questions, percentage) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                java.sql.Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(1, userId);
            statement.setInt(2, examId);
            statement.setInt(3, score);
            statement.setInt(4, totalQuestions);
            statement.setDouble(5, percentage);

            statement.executeUpdate();

            ResultSet generatedKeys =
                    statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Create result error: "
                            + e.getMessage()
            );
        }

        return -1;
    }

    public List<Result> getResultsByUser(int userId) {

        List<Result> results =
                new ArrayList<>();

        String sql =
                "SELECT * FROM results " +
                        "WHERE user_id = ? " +
                        "ORDER BY completed_at DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                Result examResult =
                        new Result();

                examResult.setId(
                        result.getInt("id")
                );

                examResult.setUserId(
                        result.getInt("user_id")
                );

                examResult.setExamId(
                        result.getInt("exam_id")
                );

                examResult.setScore(
                        result.getInt("score")
                );

                examResult.setTotalQuestions(
                        result.getInt("total_questions")
                );

                examResult.setPercentage(
                        result.getDouble("percentage")
                );

                results.add(examResult);
            }

        } catch (Exception e) {

            System.out.println(
                    "Get user results error: "
                            + e.getMessage()
            );
        }

        return results;
    }

    public List<Object[]> getResultHistory(int userId) {

        List<Object[]> results =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "r.id, " +
                        "e.title, " +
                        "e.subject, " +
                        "r.score, " +
                        "r.total_questions, " +
                        "r.percentage, " +
                        "r.completed_at " +
                        "FROM results r " +
                        "JOIN exams e " +
                        "ON r.exam_id = e.id " +
                        "WHERE r.user_id = ? " +
                        "ORDER BY r.completed_at DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, userId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                results.add(
                        new Object[]{
                                result.getInt("id"),
                                result.getString("title"),
                                result.getString("subject"),
                                result.getInt("score"),
                                result.getInt("total_questions"),
                                result.getDouble("percentage"),
                                result.getTimestamp("completed_at")
                        }
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Get result history error: "
                            + e.getMessage()
            );
        }

        return results;
    }

}
