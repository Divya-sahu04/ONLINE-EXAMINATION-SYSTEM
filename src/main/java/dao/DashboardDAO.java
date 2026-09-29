package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    public int getTotalStudents() {

        return getCount(
                "SELECT COUNT(*) FROM users WHERE role = 'STUDENT'"
        );
    }


    public int getTotalExams() {

        return getCount(
                "SELECT COUNT(*) FROM exams"
        );
    }


    public int getTotalQuestions() {

        return getCount(
                "SELECT COUNT(*) FROM questions"
        );
    }


    public int getCompletedAttempts() {

        return getCount(
                "SELECT COUNT(*) " +
                        "FROM exam_attempts " +
                        "WHERE status = 'COMPLETED'"
        );
    }


    private int getCount(String sql) {

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            if (result.next()) {

                return result.getInt(1);
            }

        } catch (Exception e) {

            System.out.println(
                    "Dashboard statistics error: "
                            + e.getMessage()
            );
        }

        return 0;
    }
}
