package com.onlineexam.ui;

import com.onlineexam.dao.ExamAttemptDAO;
import com.onlineexam.model.Exam;
import com.onlineexam.model.User;

import javax.swing.*;
import java.awt.*;

public class ExamInstructionsFrame extends JFrame {

    private User user;

    private Exam exam;

    private ExamAttemptDAO examAttemptDAO;



    public ExamInstructionsFrame(
            User user,
            Exam exam) {

        this.user = user;

        this.exam = exam;

        examAttemptDAO = new ExamAttemptDAO();

        setTitle("Exam Instructions");

        setSize(700, 550);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void startExam() {

        int attemptId =
                examAttemptDAO.startAttempt(
                        user.getId(),
                        exam.getId()
                );


        // Database/system error
        if (attemptId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to start the exam. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // Student already has an active attempt
        if (attemptId == -2) {

            JOptionPane.showMessageDialog(
                    this,
                    "You already have an active attempt for this exam.",
                    "Exam Already Started",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Successfully created a new attempt
        new ExamFrame(
                user,
                exam,
                attemptId
        );

        dispose();
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(15, 15)
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "EXAM INSTRUCTIONS",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // =========================
        // EXAM INFORMATION
        // =========================

        JPanel infoPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                5,
                                5
                        )
                );

        infoPanel.add(
                new JLabel(
                        "Exam: "
                                + exam.getTitle()
                )
        );

        infoPanel.add(
                new JLabel(
                        "Subject: "
                                + exam.getSubject()
                )
        );

        infoPanel.add(
                new JLabel(
                        "Duration: "
                                + exam.getDurationMinutes()
                                + " minutes"
                )
        );

        infoPanel.add(
                new JLabel(
                        "Total Questions: "
                                + exam.getTotalQuestions()
                )
        );

        mainPanel.add(
                infoPanel,
                BorderLayout.CENTER
        );

        // =========================
        // INSTRUCTIONS
        // =========================

        JTextArea instructions =
                new JTextArea();

        instructions.setEditable(false);

        instructions.setLineWrap(true);

        instructions.setWrapStyleWord(true);

        instructions.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        instructions.setText(
                "Please read the following instructions carefully:\n\n"
                        + "1. The examination has a fixed time limit.\n\n"
                        + "2. The timer starts when you click Start Exam.\n\n"
                        + "3. Select one answer for each question.\n\n"
                        + "4. You can move between questions using Next and Previous.\n\n"
                        + "5. You can mark questions for review.\n\n"
                        + "6. The examination will be automatically submitted when the timer reaches zero.\n\n"
                        + "7. Once submitted, your answers will be evaluated automatically.\n\n"
                        + "8. Make sure you submit your examination before leaving the examination screen."
        );

        JScrollPane instructionScroll =
                new JScrollPane(instructions);

        instructionScroll.setPreferredSize(
                new Dimension(
                        600,
                        250
                )
        );

        mainPanel.add(
                instructionScroll,
                BorderLayout.CENTER

        );

        // =========================
        // START BUTTON
        // =========================

        JButton startButton =
                new JButton("Start Exam");

        startButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(startButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.PAGE_END
        );

        add(mainPanel);

        // =========================
        // EVENT
        // =========================

        startButton.addActionListener(
                e -> startExam()
        );
    }
}
