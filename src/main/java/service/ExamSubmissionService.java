package com.onlineexam.service;

import com.onlineexam.config.DatabaseConnection;
import com.onlineexam.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

public class ExamSubmissionService {

    public int submitExam(
            int userId,
            int examId,
            int attemptId,
            int score,
            int totalQuestions,
            double percentage,
            List<Question> questions,
            String[] selectedAnswers
    ) {

        Connection connection = null;

        try {

            // Get ONE database connection
            connection =
                    DatabaseConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            // 1. Create result
            int resultId =
                    createResult(
                            connection,
                            userId,
                            examId,
                            score,
                            totalQuestions,
                            percentage
                    );

            if (resultId == -1) {

                throw new SQLException(
                        "Unable to create result."
                );
            }

            // 2. Save all answers
            saveAnswers(
                    connection,
                    resultId,
                    questions,
                    selectedAnswers
            );

            // 3. Mark exam attempt as completed
            completeAttempt(
                    connection,
                    attemptId
            );

            // Everything succeeded
            connection.commit();

            return resultId;

        } catch (Exception e) {

            // Something failed
            if (connection != null) {

                try {

                    connection.rollback();

                    System.out.println(
                            "Transaction rolled back."
                    );

                } catch (SQLException rollbackError) {

                    System.out.println(
                            "Rollback error: "
                                    + rollbackError.getMessage()
                    );
                }
            }

            System.out.println(
                    "Exam submission error: "
                            + e.getMessage()
            );

            return -1;

        } finally {

            // Close connection
            if (connection != null) {

                try {

                    connection.close();

                } catch (SQLException closeError) {

                    System.out.println(
                            "Connection close error: "
                                    + closeError.getMessage()
                    );
                }
            }
        }
    }


    private int createResult(
            Connection connection,
            int userId,
            int examId,
            int score,
            int totalQuestions,
            double percentage
    ) throws SQLException {

        String sql =
                "INSERT INTO results " +
                        "(user_id, exam_id, score, total_questions, percentage) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    userId
            );

            statement.setInt(
                    2,
                    examId
            );

            statement.setInt(
                    3,
                    score
            );

            statement.setInt(
                    4,
                    totalQuestions
            );

            statement.setDouble(
                    5,
                    percentage
            );

            statement.executeUpdate();

            try (
                    ResultSet generatedKeys =
                            statement.getGeneratedKeys()
            ) {

                if (generatedKeys.next()) {

                    return generatedKeys.getInt(1);
                }
            }
        }

        return -1;
    }


    private void saveAnswers(
            Connection connection,
            int resultId,
            List<Question> questions,
            String[] selectedAnswers
    ) throws SQLException {

        String sql =
                "INSERT INTO answers " +
                        "(result_id, question_id, selected_option, is_correct) " +
                        "VALUES (?, ?, ?, ?)";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            for (int i = 0;
                 i < questions.size();
                 i++) {

                String selectedOption =
                        selectedAnswers[i];

                String correctOption =
                        questions
                                .get(i)
                                .getCorrectOption();

                boolean isCorrect =
                        selectedOption != null &&
                                selectedOption.equalsIgnoreCase(
                                        correctOption
                                );

                statement.setInt(
                        1,
                        resultId
                );

                statement.setInt(
                        2,
                        questions
                                .get(i)
                                .getId()
                );

                if (selectedOption == null) {

                    statement.setNull(
                            3,
                            Types.CHAR
                    );

                } else {

                    statement.setString(
                            3,
                            selectedOption
                    );
                }

                statement.setBoolean(
                        4,
                        isCorrect
                );

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }


    private void completeAttempt(
            Connection connection,
            int attemptId
    ) throws SQLException {

        String sql =
                "UPDATE exam_attempts " +
                        "SET status = 'COMPLETED', " +
                        "submitted_at = CURRENT_TIMESTAMP " +
                        "WHERE id = ?";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    attemptId
            );

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated == 0) {

                throw new SQLException(
                        "Exam attempt not found."
                );
            }
        }
    }
}