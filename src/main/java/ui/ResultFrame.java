package com.onlineexam.ui;

import com.onlineexam.model.Exam;
import com.onlineexam.model.User;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {

    private User user;
    private Exam exam;

    private int score;
    private int totalQuestions;
    private double percentage;

    public ResultFrame(
            User user,
            Exam exam,
            int score,
            int totalQuestions,
            double percentage) {

        this.user = user;
        this.exam = exam;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;

        setTitle("Exam Result");
        setSize(600, 500);
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 25, 25, 25
                )
        );

        JLabel title =
                new JLabel(
                        "EXAM RESULT",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel resultPanel =
                new JPanel();

        resultPanel.setLayout(
                new BoxLayout(
                        resultPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel examLabel =
                new JLabel(
                        "Exam: " +
                                exam.getTitle()
                );

        examLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        examLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subjectLabel =
                new JLabel(
                        "Subject: " +
                                exam.getSubject()
                );

        subjectLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        subjectLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel scoreLabel =
                new JLabel(
                        "Score: " +
                                score +
                                " / " +
                                totalQuestions
                );

        scoreLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        scoreLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel percentageLabel =
                new JLabel(
                        String.format(
                                "Percentage: %.2f%%",
                                percentage
                        )
                );

        percentageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        percentageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        resultPanel.add(examLabel);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(subjectLabel);
        resultPanel.add(Box.createVerticalStrut(30));
        resultPanel.add(scoreLabel);
        resultPanel.add(Box.createVerticalStrut(15));
        resultPanel.add(percentageLabel);

        mainPanel.add(
                resultPanel,
                BorderLayout.CENTER
        );

        JButton dashboardButton =
                new JButton(
                        "Back to Dashboard"
                );

        dashboardButton.setPreferredSize(
                new Dimension(180, 40)
        );

        dashboardButton.addActionListener(
                e -> {
                    new StudentDashboard(user);
                    dispose();
                }
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                dashboardButton
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }
}
