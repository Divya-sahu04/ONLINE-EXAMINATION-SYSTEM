package com.onlineexam.ui;

import com.onlineexam.model.User;
import com.onlineexam.util.UIStyle;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private User user;

    public StudentDashboard(User user) {

        this.user = user;



        setTitle("Student Dashboard");

        setSize(800, 600);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        JLabel title =
                new JLabel(
                        "STUDENT DASHBOARD",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JLabel welcome =
                new JLabel(
                        "Welcome, " + user.getName()
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        headerPanel.add(
                title,
                BorderLayout.CENTER
        );

        headerPanel.add(
                welcome,
                BorderLayout.WEST
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                15,
                                15
                        )
                );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        50,
                        150,
                        50,
                        150
                )
        );

        JButton availableExamsButton =
                new JButton("Available Exams");

        JButton resultsButton =
                new JButton("My Results");


        JButton profileButton =
                new JButton("My Profile");

        JButton logoutButton =
                new JButton("Logout");

        UIStyle.styleButton(
                availableExamsButton
        );

        UIStyle.styleButton(
                resultsButton
        );

        UIStyle.styleButton(
                profileButton
        );

        UIStyle.styleButton(
                logoutButton
        );



        resultsButton.addActionListener(
                e -> new ResultHistoryFrame(user)
        );

        profileButton.addActionListener(
                e -> new ProfileFrame(user)
        );

        buttonPanel.add(
                availableExamsButton
        );

        buttonPanel.add(
                resultsButton
        );

        buttonPanel.add(
                profileButton
        );

        buttonPanel.add(
                logoutButton
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        add(
                buttonPanel,
                BorderLayout.CENTER
        );

        availableExamsButton.addActionListener(
                e -> new AvailableExamsFrame(user)
        );



        // =========================
        // LOGOUT
        // =========================

        logoutButton.addActionListener(
                e -> {

                    new LoginFrame();

                    dispose();
                }
        );

        setVisible(true);
    }
}
