package com.onlineexam.ui;

import com.onlineexam.dao.AnswerDAO;
import com.onlineexam.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnswerReviewFrame extends JFrame {

    private User user;

    private int resultId;

    private AnswerDAO answerDAO;

    private JPanel answersPanel;

    public AnswerReviewFrame(
            User user,
            int resultId) {

        this.user = user;
        this.resultId = resultId;

        answerDAO =
                new AnswerDAO();

        setTitle("Answer Review");

        setSize(
                800,
                650
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadAnswers();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JLabel title =
                new JLabel(
                        "ANSWER REVIEW",
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

        answersPanel =
                new JPanel();

        answersPanel.setLayout(
                new BoxLayout(
                        answersPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        answersPanel
                );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton closeButton =
                new JButton(
                        "Close"
                );

        closeButton.addActionListener(
                e -> dispose()
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                closeButton
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    private void loadAnswers() {

        answersPanel.removeAll();

        List<Object[]> answers =
                answerDAO.getAnswersByResult(
                        resultId
                );

        int questionNumber = 1;

        for (Object[] answer : answers) {

            answersPanel.add(
                    createQuestionPanel(
                            answer,
                            questionNumber
                    )
            );

            questionNumber++;
        }

        answersPanel.revalidate();
        answersPanel.repaint();
    }

    private JPanel createQuestionPanel(
            Object[] answer,
            int questionNumber
    ) {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                java.awt.Color.LIGHT_GRAY
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        // Question
        JLabel questionLabel =
                new JLabel(
                        "<html><b>Question "
                                + questionNumber
                                + ":</b> "
                                + answer[1]
                                + "</html>"
                );

        questionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );


        // Option texts
        String optionA =
                answer[2].toString();

        String optionB =
                answer[3].toString();

        String optionC =
                answer[4].toString();

        String optionD =
                answer[5].toString();


        // Selected and correct options
        String selectedOption =
                (String) answer[6];

        String correctOption =
                (String) answer[7];

        boolean isCorrect =
                (Boolean) answer[8];


        String selectedText =
                getOptionText(
                        selectedOption,
                        optionA,
                        optionB,
                        optionC,
                        optionD
                );

        String correctText =
                getOptionText(
                        correctOption,
                        optionA,
                        optionB,
                        optionC,
                        optionD
                );


        JLabel selectedLabel;

        if (selectedOption == null ||
                selectedOption.trim().isEmpty()) {

            selectedLabel =
                    new JLabel(
                            "<html><b>Your Answer:</b> "
                                    + "Not Answered"
                                    + "</html>"
                    );

        } else {

            selectedLabel =
                    new JLabel(
                            "<html><b>Your Answer:</b> "
                                    + selectedOption
                                    + ". "
                                    + selectedText
                                    + "</html>"
                    );
        }


        JLabel correctLabel =
                new JLabel(
                        "<html><b>Correct Answer:</b> "
                                + correctOption
                                + ". "
                                + correctText
                                + "</html>"
                );


        JLabel statusLabel;


        if (selectedOption == null ||
                selectedOption.trim().isEmpty()) {

            statusLabel =
                    new JLabel(
                            "⚪ UNANSWERED"
                    );

        } else if (isCorrect) {

            statusLabel =
                    new JLabel(
                            "✓ CORRECT"
                    );

        } else {

            statusLabel =
                    new JLabel(
                            "✗ INCORRECT"
                    );
        }


        statusLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );


        panel.add(questionLabel);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(selectedLabel);

        panel.add(
                Box.createVerticalStrut(5)
        );

        panel.add(correctLabel);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(statusLabel);


        return panel;
    }

    private String getOptionText(
            String option,
            String optionA,
            String optionB,
            String optionC,
            String optionD
    ) {

        if (option == null) {
            return "";
        }

        switch (option.toUpperCase()) {

            case "A":
                return optionA;

            case "B":
                return optionB;

            case "C":
                return optionC;

            case "D":
                return optionD;

            default:
                return "";
        }
    }

    private String formatAnswer(
            String answer) {

        if (answer == null ||
                answer.trim().isEmpty()) {

            return "Not Answered";
        }

        return answer;
    }
}
