package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExamAttemptDAO {

    /**
     * Creates a new exam attempt when a student starts an exam.
     *
     * @param userId ID of the student
     * @param examId ID of the exam
     * @return generated attempt ID, or -1 if creation fails
     */
    public int startAttempt(
            int userId,
            int examId
    ) {

        String checkSql =
                "SELECT id " +
                        "FROM exam_attempts " +
                        "WHERE user_id = ? " +
                        "AND exam_id = ? " +
                        "AND status = 'IN_PROGRESS' " +
                        "LIMIT 1";

        String insertSql =
                "INSERT INTO exam_attempts " +
                        "(user_id, exam_id, status) " +
                        "VALUES (?, ?, 'IN_PROGRESS')";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement checkStatement =
                        connection.prepareStatement(
                                checkSql
                        )
        ) {

            // Check for an existing active attempt
            checkStatement.setInt(
                    1,
                    userId
            );

            checkStatement.setInt(
                    2,
                    examId
            );

            ResultSet result =
                    checkStatement.executeQuery();

            if (result.next()) {

                System.out.println(
                        "Student already has an active attempt."
                );

                return -2;
            }


            // No active attempt exists.
            // Create a new attempt.
            try (
                    PreparedStatement insertStatement =
                            connection.prepareStatement(
                                    insertSql,
                                    java.sql.Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                insertStatement.setInt(
                        1,
                        userId
                );

                insertStatement.setInt(
                        2,
                        examId
                );

                insertStatement.executeUpdate();

                ResultSet generatedKeys =
                        insertStatement.getGeneratedKeys();

                if (generatedKeys.next()) {

                    return generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Start attempt error: "
                            + e.getMessage()
            );
        }

        return -1;
    }


    /**
     * Marks an exam attempt as completed.
     *
     * @param attemptId ID of the attempt
     * @return true if update succeeds
     */
    public boolean completeAttempt(int attemptId) {

        String sql =
                "UPDATE exam_attempts " +
                        "SET status = 'COMPLETED', " +
                        "submitted_at = CURRENT_TIMESTAMP " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, attemptId);

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (Exception e) {

            System.out.println(
                    "Complete attempt error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}