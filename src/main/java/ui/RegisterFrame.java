package com.onlineexam.ui;

import com.onlineexam.dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterFrame() {

        setTitle("Student Registration");

        setSize(500, 450);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "STUDENT REGISTRATION",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        10,
                        20,
                        10
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =========================
        // FORM
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                15
                        )
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        50,
                        20,
                        50
                )
        );

        // Name

        formPanel.add(
                new JLabel("Name:")
        );

        nameField =
                new JTextField();

        formPanel.add(nameField);

        // Email

        formPanel.add(
                new JLabel("Email:")
        );

        emailField =
                new JTextField();

        formPanel.add(emailField);

        // Password

        formPanel.add(
                new JLabel("Password:")
        );

        passwordField =
                new JPasswordField();

        formPanel.add(passwordField);

        // Confirm Password

        formPanel.add(
                new JLabel("Confirm Password:")
        );

        confirmPasswordField =
                new JPasswordField();

        formPanel.add(confirmPasswordField);

        // Buttons

        JButton registerButton =
                new JButton("Register");

        JButton backButton =
                new JButton("Back to Login");

        formPanel.add(registerButton);
        formPanel.add(backButton);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // BUTTON ACTIONS
        // =========================

        registerButton.addActionListener(
                e -> register()
        );

        backButton.addActionListener(
                e -> backToLogin()
        );

        setVisible(true);
    }

    // =========================
    // REGISTER METHOD
    // =========================

    private void register() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        // =========================
        // VALIDATION
        // =========================
        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (name.length() < 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name must contain at least 2 characters.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your email.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }



        // =========================
        // DATABASE REGISTRATION
        // =========================

        UserDAO userDAO =
                new UserDAO();

        boolean registered =
                userDAO.register(
                        name,
                        email,
                        password
                );

        if (registered) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!\nYou can now login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            new LoginFrame();

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed.\nEmail may already exist.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // BACK TO LOGIN
    // =========================

    private void backToLogin() {

        new LoginFrame();

        dispose();
    }
}