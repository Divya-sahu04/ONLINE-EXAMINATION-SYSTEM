package com.onlineexam.ui;

import com.onlineexam.dao.UserDAO;
import com.onlineexam.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Online Examination System");

        setSize(500, 350);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        JLabel title =
                new JLabel(
                        "ONLINE EXAMINATION SYSTEM",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                10,
                                15
                        )
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        50,
                        30,
                        50
                )
        );

        formPanel.add(
                new JLabel("Email:")
        );

        emailField =
                new JTextField();

        formPanel.add(emailField);

        formPanel.add(
                new JLabel("Password:")
        );

        passwordField =
                new JPasswordField();

        formPanel.add(passwordField);

        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton("Register");

        formPanel.add(loginButton);

        formPanel.add(registerButton);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        loginButton.addActionListener(
                e ->  login()

                    );

        registerButton.addActionListener(
                e -> openRegistration()
        );

        setVisible(true);
    }

    private void login() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (
                email.isEmpty()
                        ||
                        password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password."
            );

            return;
        }

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.login(
                        email,
                        password
                );

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Login successful!"
        );

        if (user.getRole().equals("ADMIN")) {

            new AdminDashboard(user);

        } else {

            new StudentDashboard(user);
        }

        dispose();
    }



    private void openRegistration() {

        new RegisterFrame();

        dispose();
    }
}
