package com.onlineexam.ui;

import com.onlineexam.dao.ExamDAO;
import com.onlineexam.dao.QuestionDAO;
import com.onlineexam.model.Exam;
import com.onlineexam.model.Question;
import com.onlineexam.util.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QuestionManagementFrame extends JFrame {

    private JComboBox<ExamItem> examComboBox;

    private JTable questionTable;

    private DefaultTableModel tableModel;

    private JTextArea questionTextArea;

    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;

    private JComboBox<String> correctOptionComboBox;

    private QuestionDAO questionDAO;
    private ExamDAO examDAO;

    private int selectedQuestionId = -1;


    public QuestionManagementFrame() {

        questionDAO = new QuestionDAO();
        examDAO = new ExamDAO();

        setTitle("Question Management");

        setSize(1100, 700);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadExams();

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
                        10,
                        10,
                        10,
                        10
                )
        );


        // =================================================
        // TITLE
        // =================================================

        JLabel titleLabel =
                new JLabel(
                        "QUESTION MANAGEMENT",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );


        // =================================================
        // CENTER TABLE
        // =================================================

        String[] columns = {
                "ID",
                "Question",
                "Option A",
                "Option B",
                "Option C",
                "Option D",
                "Correct"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        questionTable =
                new JTable(tableModel);

        UIStyle.styleTable(
                questionTable
        );

        questionTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        questionTable.setRowHeight(35);

        JScrollPane tableScrollPane =
                new JScrollPane(questionTable);

        mainPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );


        // =================================================
        // FORM PANEL
        // =================================================

        JPanel formPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );


        // =================================================
        // EXAM SELECTOR
        // =================================================

        JPanel examPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        examPanel.add(
                new JLabel("Select Exam:")
        );

        examComboBox =
                new JComboBox<>();

        examComboBox.setPreferredSize(
                new Dimension(400, 30)
        );

        examPanel.add(
                examComboBox
        );

        formPanel.add(
                examPanel,
                BorderLayout.NORTH
        );


        // =================================================
        // QUESTION INPUT
        // =================================================

        JPanel inputPanel =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                10,
                                10
                        )
                );


        inputPanel.add(
                new JLabel("Question:")
        );

        questionTextArea =
                new JTextArea();

        questionTextArea.setLineWrap(true);

        questionTextArea.setWrapStyleWord(true);

        JScrollPane questionScroll =
                new JScrollPane(
                        questionTextArea
                );

        inputPanel.add(questionScroll);


        inputPanel.add(
                new JLabel("Option A:")
        );

        optionAField =
                new JTextField();

        inputPanel.add(optionAField);


        inputPanel.add(
                new JLabel("Option B:")
        );

        optionBField =
                new JTextField();

        inputPanel.add(optionBField);


        inputPanel.add(
                new JLabel("Option C:")
        );

        optionCField =
                new JTextField();

        inputPanel.add(optionCField);


        inputPanel.add(
                new JLabel("Option D:")
        );

        optionDField =
                new JTextField();

        inputPanel.add(optionDField);


        inputPanel.add(
                new JLabel("Correct Option:")
        );

        correctOptionComboBox =
                new JComboBox<>(
                        new String[]{
                                "A",
                                "B",
                                "C",
                                "D"
                        }
                );

        inputPanel.add(
                correctOptionComboBox
        );


        formPanel.add(
                inputPanel,
                BorderLayout.CENTER
        );


        // =================================================
        // BUTTONS
        // =================================================

        JButton addButton =
                new JButton("Add Question");

        JButton updateButton =
                new JButton("Update Question");

        JButton deleteButton =
                new JButton("Delete Question");

        JButton clearButton =
                new JButton("Clear");


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );

        buttonPanel.add(addButton);

        buttonPanel.add(updateButton);

        buttonPanel.add(deleteButton);

        buttonPanel.add(clearButton);


        formPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        mainPanel.add(
                formPanel,
                BorderLayout.SOUTH
        );


        add(mainPanel);


        // =================================================
        // EVENTS
        // =================================================

        examComboBox.addActionListener(
                e -> loadQuestions()
        );

        addButton.addActionListener(
                e -> addQuestion()
        );

        updateButton.addActionListener(
                e -> updateQuestion()
        );

        deleteButton.addActionListener(
                e -> deleteQuestion()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        questionTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> loadSelectedQuestion()
                );
    }


    // =====================================================
    // LOAD EXAMS
    // =====================================================

    private void loadExams() {

        examComboBox.removeAllItems();

        List<Exam> exams =
                examDAO.getAllExams();

        for (Exam exam : exams) {

            examComboBox.addItem(
                    new ExamItem(exam)
            );
        }

        if (examComboBox.getItemCount() > 0) {

            examComboBox.setSelectedIndex(0);

            loadQuestions();
        }
    }


    // =====================================================
    // LOAD QUESTIONS
    // =====================================================

    private void loadQuestions() {

        tableModel.setRowCount(0);

        clearFieldsOnly();

        ExamItem selectedExam =
                (ExamItem)
                        examComboBox.getSelectedItem();

        if (selectedExam == null) {
            return;
        }

        int examId =
                selectedExam.getExam().getId();

        List<Question> questions =
                questionDAO.getQuestionsByExam(
                        examId
                );

        for (Question question : questions) {

            tableModel.addRow(
                    new Object[]{
                            question.getId(),
                            question.getQuestionText(),
                            question.getOptionA(),
                            question.getOptionB(),
                            question.getOptionC(),
                            question.getOptionD(),
                            question.getCorrectOption()
                    }
            );
        }
    }


    // =====================================================
    // ADD QUESTION
    // =====================================================

    private void addQuestion() {

        ExamItem selectedExam =
                (ExamItem)
                        examComboBox.getSelectedItem();

        if (selectedExam == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam."
            );

            return;
        }

        String questionText =
                questionTextArea
                        .getText()
                        .trim();

        String optionA =
                optionAField
                        .getText()
                        .trim();

        String optionB =
                optionBField
                        .getText()
                        .trim();

        String optionC =
                optionCField
                        .getText()
                        .trim();

        String optionD =
                optionDField
                        .getText()
                        .trim();

        String correctOption =
                correctOptionComboBox
                        .getSelectedItem()
                        .toString();


        if (
                questionText.isEmpty()
                        ||
                        optionA.isEmpty()
                        ||
                        optionB.isEmpty()
                        ||
                        optionC.isEmpty()
                        ||
                        optionD.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all question fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int examId =
                selectedExam
                        .getExam()
                        .getId();


        boolean success =
                questionDAO.addQuestion(
                        examId,
                        questionText,
                        optionA,
                        optionB,
                        optionC,
                        optionD,
                        correctOption
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Question added successfully!"
            );

            loadQuestions();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add question.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =====================================================
    // UPDATE QUESTION
    // =====================================================

    private void updateQuestion() {

        if (selectedQuestionId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a question first."
            );

            return;
        }


        String questionText =
                questionTextArea
                        .getText()
                        .trim();

        String optionA =
                optionAField
                        .getText()
                        .trim();

        String optionB =
                optionBField
                        .getText()
                        .trim();

        String optionC =
                optionCField
                        .getText()
                        .trim();

        String optionD =
                optionDField
                        .getText()
                        .trim();

        String correctOption =
                correctOptionComboBox
                        .getSelectedItem()
                        .toString();


        if (
                questionText.isEmpty()
                        ||
                        optionA.isEmpty()
                        ||
                        optionB.isEmpty()
                        ||
                        optionC.isEmpty()
                        ||
                        optionD.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        if (optionA.equalsIgnoreCase(optionB) ||
                optionA.equalsIgnoreCase(optionC) ||
                optionA.equalsIgnoreCase(optionD) ||
                optionB.equalsIgnoreCase(optionC) ||
                optionB.equalsIgnoreCase(optionD) ||
                optionC.equalsIgnoreCase(optionD)) {

            JOptionPane.showMessageDialog(
                    this,
                    "All four options should be different.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        boolean success =
                questionDAO.updateQuestion(
                        selectedQuestionId,
                        questionText,
                        optionA,
                        optionB,
                        optionC,
                        optionD,
                        correctOption
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Question updated successfully!"
            );

            loadQuestions();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update question.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =====================================================
    // DELETE QUESTION
    // =====================================================

    private void deleteQuestion() {

        if (selectedQuestionId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a question first."
            );

            return;
        }


        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this question?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                confirmation
                        !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }


        ExamItem selectedExam =
                (ExamItem)
                        examComboBox.getSelectedItem();


        int examId =
                selectedExam
                        .getExam()
                        .getId();


        boolean success =
                questionDAO.deleteQuestion(
                        selectedQuestionId,
                        examId
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Question deleted successfully!"
            );

            loadQuestions();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete question.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =====================================================
    // LOAD SELECTED QUESTION
    // =====================================================

    private void loadSelectedQuestion() {

        int selectedRow =
                questionTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }


        selectedQuestionId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );


        questionTextArea.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString()
        );

        optionAField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString()
        );

        optionBField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );

        optionCField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString()
        );

        optionDField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString()
        );

        correctOptionComboBox.setSelectedItem(
                tableModel
                        .getValueAt(
                                selectedRow,
                                6
                        )
                        .toString()
        );
    }


    // =====================================================
    // CLEAR FIELDS
    // =====================================================

    private void clearFields() {

        clearFieldsOnly();

        questionTable.clearSelection();
    }


    private void clearFieldsOnly() {

        selectedQuestionId = -1;

        questionTextArea.setText("");

        optionAField.setText("");

        optionBField.setText("");

        optionCField.setText("");

        optionDField.setText("");

        correctOptionComboBox.setSelectedIndex(0);
    }


    // =====================================================
    // EXAM ITEM CLASS
    // =====================================================

    private static class ExamItem {

        private Exam exam;

        public ExamItem(Exam exam) {

            this.exam = exam;
        }

        public Exam getExam() {

            return exam;
        }

        @Override
        public String toString() {

            return exam.getTitle()
                    + " - "
                    + exam.getSubject();
        }
    }
}