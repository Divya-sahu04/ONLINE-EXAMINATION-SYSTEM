package com.onlineexam.ui;

import com.onlineexam.model.User;
import com.onlineexam.dao.DashboardDAO;
import com.onlineexam.util.UIStyle;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private User user;
    private DashboardDAO dashboardDAO;

    public AdminDashboard(User user) {

        this.user = user;
        dashboardDAO =
                new DashboardDAO();

        setTitle("Admin Dashboard");

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
                        "ADMIN DASHBOARD",
                        SwingConstants.CENTER
                );

        UIStyle.styleTitle(title);

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
        // BUTTONS
        // =========================
        JPanel statsPanel =
                createStatsPanel();

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                1,
                                15,
                                15
                        )
                );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        150,
                        40,
                        150
                )
        );

        JButton examManagementButton =
                new JButton("Manage Exams");

        JButton questionManagementButton =
                new JButton("Manage Questions");

        JButton studentsButton =
                new JButton("View Students");

        JButton resultsButton =
                new JButton("View Results");

        JButton logoutButton =
                new JButton("Logout");

        UIStyle.styleButton(
                examManagementButton
        );

        UIStyle.styleButton(
                questionManagementButton
        );

        UIStyle.styleButton(
                studentsButton
        );

        UIStyle.styleButton(
                resultsButton
        );

        UIStyle.styleButton(
                logoutButton
        );



        studentsButton.addActionListener(
                e -> {
                    System.out.println("View Students clicked!");
                    new StudentManagementFrame();
                }
        );

        resultsButton.addActionListener(
                e -> new ResultManagementFrame()
        );

        buttonPanel.add(
                examManagementButton
        );

        buttonPanel.add(
                questionManagementButton
        );

        buttonPanel.add(
                studentsButton
        );

        buttonPanel.add(
                resultsButton
        );

        buttonPanel.add(
                logoutButton
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        30,
                        15,
                        30
                )
        );

        centerPanel.add(
                statsPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );
         //EXAM MANAGEMENT
        examManagementButton.addActionListener(
                e -> new ExamManagementFrame()
        );

         //QUESTION MANAGEMENT
        questionManagementButton.addActionListener(
                e -> new QuestionManagementFrame()
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
    private JPanel createStatsPanel() {

        int totalStudents =
                dashboardDAO.getTotalStudents();

        int totalExams =
                dashboardDAO.getTotalExams();

        int totalQuestions =
                dashboardDAO.getTotalQuestions();

        int completedAttempts =
                dashboardDAO.getCompletedAttempts();


        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                15
                        )
                );


        statsPanel.add(
                createStatCard(
                        "Students",
                        totalStudents
                )
        );

        statsPanel.add(
                createStatCard(
                        "Exams",
                        totalExams
                )
        );

        statsPanel.add(
                createStatCard(
                        "Questions",
                        totalQuestions
                )
        );

        statsPanel.add(
                createStatCard(
                        "Completed Attempts",
                        completedAttempts
                )
        );


        return statsPanel;
    }
    private JPanel createStatCard(
            String title,
            int value
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.GRAY
                        ),
                        BorderFactory.createEmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        JLabel valueLabel =
                new JLabel(
                        String.valueOf(value),
                        SwingConstants.CENTER
                );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );


        return card;
    }
}
