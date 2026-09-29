package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;
import com.onlineexam.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileDAO {

    public boolean updateName(
            int userId,
            String name
    ) {

        String sql =
                "UPDATE users " +
                        "SET name = ? " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update name error: "
                            + e.getMessage()
            );

            return false;
        }
    }


    public boolean updatePassword(
            int userId,
            String currentPassword,
            String newPassword
    ) {

        String selectSql =
                "SELECT password " +
                        "FROM users " +
                        "WHERE id = ?";

        String updateSql =
                "UPDATE users " +
                        "SET password = ? " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement selectStatement =
                        connection.prepareStatement(
                                selectSql
                        )
        ) {

            selectStatement.setInt(
                    1,
                    userId
            );

            ResultSet result =
                    selectStatement.executeQuery();

            if (!result.next()) {
                return false;
            }

            String storedHash =
                    result.getString("password");

            // Verify current password
            if (!PasswordUtil.checkPassword(
                    currentPassword,
                    storedHash
            )) {

                return false;
            }

            String newHash =
                    PasswordUtil.hashPassword(
                            newPassword
                    );

            try (
                    PreparedStatement updateStatement =
                            connection.prepareStatement(
                                    updateSql
                            )
            ) {

                updateStatement.setString(
                        1,
                        newHash
                );

                updateStatement.setInt(
                        2,
                        userId
                );

                return updateStatement
                        .executeUpdate() > 0;
            }

        } catch (Exception e) {

            System.out.println(
                    "Update password error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}
