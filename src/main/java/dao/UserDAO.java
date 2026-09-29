package com.onlineexam.dao;

import com.onlineexam.config.DatabaseConnection;
import com.onlineexam.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.onlineexam.util.PasswordUtil;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserDAO {

    public List<User> getAllStudents() {

        List<User> students =
                new ArrayList<>();

        String sql =
                "SELECT id, name, email, role, created_at " +
                        "FROM users " +
                        "WHERE role = 'STUDENT' " +
                        "ORDER BY id DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                User user =
                        new User();

                user.setId(
                        result.getInt("id")
                );

                user.setName(
                        result.getString("name")
                );

                user.setEmail(
                        result.getString("email")
                );

                user.setRole(
                        result.getString("role")
                );

                students.add(user);
            }

        } catch (Exception e) {

            System.out.println(
                    "Get students error: "
                            + e.getMessage()
            );
        }

        return students;
    }
    public boolean register(
            String name,
            String email,
            String password) {

        String sql =
                "INSERT INTO users " +
                        "(name, email, password, role) " +
                        "VALUES (?, ?, ?, 'STUDENT')";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, email);
            String hashedPassword =
                    PasswordUtil.hashPassword(password);

            statement.setString(
                    3,
                    hashedPassword
            );

            statement.executeUpdate();

            return true;

        } catch (
                SQLIntegrityConstraintViolationException e
        ) {

        System.out.println(
                "Email already exists."
        );

        return false;

    } catch (Exception e) {

        System.out.println(
                "Registration error: "
                        + e.getMessage()
        );

        return false;
    }
    }

    public User login(
            String email,
            String password) {

        String sql =
                "SELECT * FROM users " +
                        "WHERE email = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    email
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                String storedHash =
                        result.getString("password");

                boolean passwordMatches =
                        PasswordUtil.checkPassword(
                                password,
                                storedHash
                        );

                if (passwordMatches) {

                    User user =
                            new User();

                    user.setId(
                            result.getInt("id")
                    );

                    user.setName(
                            result.getString("name")
                    );

                    user.setEmail(
                            result.getString("email")
                    );

                    user.setPassword(
                            storedHash
                    );

                    user.setRole(
                            result.getString("role")
                    );

                    return user;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Login error: "
                            + e.getMessage()
            );
        }

        return null;
    }
}
