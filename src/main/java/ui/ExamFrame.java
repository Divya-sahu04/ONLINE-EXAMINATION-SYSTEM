package com.onlineexam.ui;

import com.onlineexam.dao.ExamAttemptDAO;
import com.onlineexam.dao.ResultDAO;
import com.onlineexam.dao.AnswerDAO;
import com.onlineexam.dao.QuestionDAO;
import com.onlineexam.model.Exam;
import com.onlineexam.model.Question;
import com.onlineexam.model.User;
import com.onlineexam.service.ExamSubmissionService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExamFrame extends JFrame {

    private User user;

    private Exam exam;

    private int attemptId;

    private ExamAttemptDAO examAttemptDAO;

    private ResultDAO resultDAO;

    private AnswerDAO answerDAO;

    private ExamSubmissionService examSubmissionService;

    private List<Question> questions;

    private QuestionDAO questionDAO;

    private int currentQuestionIndex = 0;

    private String[] selectedAnswers;

    private boolean[] markedForReview;

    private JLabel questionNumberLabel;

    private JLabel timerLabel;

    private JTextArea questionTextArea;

    private JRadioButton optionA;

    private JRadioButton optionB;

    private JRadioButton optionC;

    private JRadioButton optionD;

    private ButtonGroup optionGroup;

    private JButton previousButton;

    private JButton nextButton;

    private JButton reviewButton;

    private JButton submitButton;

    private Timer timer;

    private int remainingSeconds;


    public ExamFrame(
            User user,
            Exam exam,
            int attemptId
    ) {
        this.user = user;

        this.exam = exam;

        this.attemptId = attemptId;

        examAttemptDAO = new ExamAttemptDAO();

        resultDAO =
                new ResultDAO();

        questionDAO =
                new QuestionDAO();

        answerDAO =
                new AnswerDAO();
        examSubmissionService =
                new ExamSubmissionService();

        questions =
                questionDAO.getQuestionsByExam(
                        exam.getId()
                );

        if (questions.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "No questions found for this exam."
            );

            dispose();

            return;
        }

        selectedAnswers =
                new String[
                        questions.size()
                        ];

        markedForReview =
                new boolean[
                        questions.size()
                        ];

        remainingSeconds =
                exam.getDurationMinutes()
                        * 60;

        setTitle(
                exam.getTitle()
        );

        setSize(900, 650);

        setDefaultCloseOperation(
                JFrame.DO_NOTHING_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        showQuestion();

        startTimer();

        setVisible(true);
    }


    // =====================================================
    // CREATE UI
    // =====================================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );


        // =================================================
        // TOP PANEL
        // =================================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        questionNumberLabel =
                new JLabel();

        questionNumberLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        timerLabel =
                new JLabel();

        timerLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        topPanel.add(
                questionNumberLabel,
                BorderLayout.WEST
        );

        topPanel.add(
                timerLabel,
                BorderLayout.EAST
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );


        // =================================================
        // QUESTION
        // =================================================

        JPanel questionPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        questionTextArea =
                new JTextArea();

        questionTextArea.setEditable(false);

        questionTextArea.setLineWrap(true);

        questionTextArea.setWrapStyleWord(true);

        questionTextArea.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        questionTextArea.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        questionPanel.add(
                questionTextArea,
                BorderLayout.NORTH
        );


        // =================================================
        // OPTIONS
        // =================================================

        JPanel optionsPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                10,
                                10
                        )
                );

        optionA =
                new JRadioButton();

        optionB =
                new JRadioButton();

        optionC =
                new JRadioButton();

        optionD =
                new JRadioButton();


        optionGroup =
                new ButtonGroup();

        optionGroup.add(optionA);

        optionGroup.add(optionB);

        optionGroup.add(optionC);

        optionGroup.add(optionD);


        optionsPanel.add(optionA);

        optionsPanel.add(optionB);

        optionsPanel.add(optionC);

        optionsPanel.add(optionD);


        questionPanel.add(
                optionsPanel,
                BorderLayout.CENTER
        );


        mainPanel.add(
                questionPanel,
                BorderLayout.CENTER
        );


        // =================================================
        // BUTTONS
        // =================================================

        previousButton =
                new JButton("Previous");

        reviewButton =
                new JButton("Mark for Review");

        nextButton =
                new JButton("Next");

        submitButton =
                new JButton("Submit Exam");


        JPanel navigationPanel =
                new JPanel(
                        new FlowLayout()
                );

        navigationPanel.add(
                previousButton
        );

        navigationPanel.add(
                reviewButton
        );

        navigationPanel.add(
                nextButton
        );

        navigationPanel.add(
                submitButton
        );


        mainPanel.add(
                navigationPanel,
                BorderLayout.SOUTH
        );


        add(mainPanel);


        // =================================================
        // EVENTS
        // =================================================

        previousButton.addActionListener(
                e -> previousQuestion()
        );

        nextButton.addActionListener(
                e -> nextQuestion()
        );

        reviewButton.addActionListener(
                e -> markForReview()
        );

        submitButton.addActionListener(
                e -> submitExam()
        );
    }


    // =====================================================
    // SHOW QUESTION
    // =====================================================

    private void showQuestion() {

        Question question =
                questions.get(
                        currentQuestionIndex
                );

        questionNumberLabel.setText(
                "Question "
                        + (currentQuestionIndex + 1)
                        + " / "
                        + questions.size()
        );

        questionTextArea.setText(
                question.getQuestionText()
        );

        optionA.setText(
                "A. "
                        + question.getOptionA()
        );

        optionB.setText(
                "B. "
                        + question.getOptionB()
        );

        optionC.setText(
                "C. "
                        + question.getOptionC()
        );

        optionD.setText(
                "D. "
                        + question.getOptionD()
        );


        optionGroup.clearSelection();


        String selected =
                selectedAnswers[
                        currentQuestionIndex
                        ];


        if ("A".equals(selected)) {

            optionA.setSelected(true);

        } else if ("B".equals(selected)) {

            optionB.setSelected(true);

        } else if ("C".equals(selected)) {

            optionC.setSelected(true);

        } else if ("D".equals(selected)) {

            optionD.setSelected(true);
        }


        previousButton.setEnabled(
                currentQuestionIndex > 0
        );

        nextButton.setEnabled(
                currentQuestionIndex
                        <
                        questions.size() - 1
        );


        if (
                markedForReview[
                        currentQuestionIndex
                        ]
        ) {

            reviewButton.setText(
                    "Remove Review"
            );

        } else {

            reviewButton.setText(
                    "Mark for Review"
            );
        }
    }


    // =====================================================
    // SAVE CURRENT ANSWER
    // =====================================================

    private void saveCurrentAnswer() {

        if (optionA.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
                    ] = "A";

        } else if (optionB.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
                    ] = "B";

        } else if (optionC.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
                    ] = "C";

        } else if (optionD.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
                    ] = "D";
        }
    }


    // =====================================================
    // NEXT
    // =====================================================

    private void nextQuestion() {

        saveCurrentAnswer();

        if (
                currentQuestionIndex
                        <
                        questions.size() - 1
        ) {

            currentQuestionIndex++;

            showQuestion();
        }
    }


    // =====================================================
    // PREVIOUS
    // =====================================================

    private void previousQuestion() {

        saveCurrentAnswer();

        if (currentQuestionIndex > 0) {

            currentQuestionIndex--;

            showQuestion();
        }
    }


    // =====================================================
    // MARK FOR REVIEW
    // =====================================================

    private void markForReview() {

        saveCurrentAnswer();

        markedForReview[
                currentQuestionIndex
                ] =
                !markedForReview[
                        currentQuestionIndex
                        ];

        showQuestion();
    }


    // =====================================================
    // TIMER
    // =====================================================

    private void startTimer() {

        updateTimerLabel();

        timer =
                new Timer(
                        1000,
                        e -> {

                            remainingSeconds--;

                            updateTimerLabel();

                            if (
                                    remainingSeconds
                                            <=
                                            0
                            ) {

                                timer.stop();

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Time is over. Your exam will be submitted automatically."
                                );

                                submitExam();
                            }
                        }
                );

        timer.start();
    }


    private void updateTimerLabel() {

        int minutes =
                remainingSeconds / 60;

        int seconds =
                remainingSeconds % 60;

        timerLabel.setText(
                String.format(
                        "Time Remaining: %02d:%02d",
                        minutes,
                        seconds
                )
        );
    }


    // =====================================================
    // SUBMIT
    // =====================================================

    private void submitExam() {

        saveCurrentAnswer();

        if (timer != null) {

            timer.stop();
        }

        int unanswered = 0;

        for (
                String answer :
                selectedAnswers
        ) {

            if (answer == null) {

                unanswered++;
            }
        }


        if (unanswered > 0) {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "You have "
                                    + unanswered
                                    + " unanswered question(s).\n"
                                    + "Do you still want to submit?",
                            "Confirm Submission",
                            JOptionPane.YES_NO_OPTION
                    );

            if (
                    choice
                            !=
                            JOptionPane.YES_OPTION
            ) {

                startTimer();

                return;
            }
        }


        int score = calculateScore();
        double percentage =
                calculatePercentage(score);

        int resultId =
                examSubmissionService.submitExam(
                        user.getId(),
                        exam.getId(),
                        score,
                        questions.size(),
                        0,                  // missing int argument
                        percentage,
                        questions,
                        selectedAnswers


                );

        if (resultId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your exam was submitted, but the result could not be saved.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        new ResultFrame(
                user,
                exam,
                score,
                questions.size(),
                percentage
        );

        dispose();


    }


    // =====================================================
    // CALCULATE SCORE
    // =====================================================

    private int calculateScore() {

        int score = 0;

        for (
                int i = 0;
                i < questions.size();
                i++
        ) {

            String selected =
                    selectedAnswers[i];

            String correct =
                    questions
                            .get(i)
                            .getCorrectOption();

            if (
                    selected != null
                            &&
                            selected.equalsIgnoreCase(
                                    correct
                            )
            ) {

                score++;
            }
        }

        return score;
    }

    private double calculatePercentage(int score) {

        if (questions.isEmpty()) {
            return 0.0;
        }

        return ((double) score /
                questions.size()) * 100;
    }
}
