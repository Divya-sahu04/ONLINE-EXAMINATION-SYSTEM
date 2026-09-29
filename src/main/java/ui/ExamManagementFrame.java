package com.onlineexam.ui;

import com.onlineexam.dao.ExamDAO;
import com.onlineexam.model.Exam;
import com.onlineexam.util.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExamManagementFrame extends JFrame {

    private JTable examTable;

    private DefaultTableModel tableModel;

    private JTextField titleField;
    private JTextField subjectField;
    private JTextField durationField;

    private ExamDAO examDAO;

    public ExamManagementFrame() {

        examDAO = new ExamDAO();

        setTitle("Exam Management");

        setSize(900, 600);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // =========================
        // MAIN PANEL
        // =========================

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

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "EXAM MANAGEMENT",
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

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "ID",
                "Title",
                "Subject",
                "Duration",
                "Questions"
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

        examTable =
                new JTable(tableModel);

        UIStyle.styleTable(
                examTable
        );

        examTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(examTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // FORM
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                10,
                                10
                        )
                );

        titleField =
                new JTextField();

        subjectField =
                new JTextField();

        durationField =
                new JTextField();

        formPanel.add(
                new JLabel("Exam Title:")
        );

        formPanel.add(
                new JLabel("Subject:")
        );

        formPanel.add(
                new JLabel("Duration (minutes):")
        );

        formPanel.add(titleField);

        formPanel.add(subjectField);

        formPanel.add(durationField);

        // =========================
        // BUTTONS
        // =========================

        JButton addButton =
                new JButton("Add Exam");

        JButton updateButton =
                new JButton("Update Exam");

        JButton deleteButton =
                new JButton("Delete Exam");

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

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.add(
                formPanel,
                BorderLayout.NORTH
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // BUTTON EVENTS
        // =========================

        addButton.addActionListener(
                e -> addExam()
        );

        updateButton.addActionListener(
                e -> updateExam()
        );

        deleteButton.addActionListener(
                e -> deleteExam()
        );

        clearButton.addActionListener(
                e -> clearFields()
        );

        // =========================
        // TABLE CLICK
        // =========================

        examTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> loadSelectedExam()
                );

        loadExams();

        setVisible(true);
    }


    // =========================
    // LOAD EXAMS
    // =========================

    private void loadExams() {

        tableModel.setRowCount(0);

        List<Exam> exams =
                examDAO.getAllExams();

        for (Exam exam : exams) {

            tableModel.addRow(
                    new Object[]{
                            exam.getId(),
                            exam.getTitle(),
                            exam.getSubject(),
                            exam.getDurationMinutes(),
                            exam.getTotalQuestions()
                    }
            );
        }
    }


    // =========================
    // ADD EXAM
    // =========================

    private void addExam() {

        String title =
                titleField.getText().trim();

        String subject =
                subjectField.getText().trim();

        String durationText =
                durationField.getText().trim();

        if (title.isEmpty() || subject.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exam title and subject are required."
            );

            return;
        }

        int duration;

        try {

            duration = Integer.parseInt(durationText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Duration must be a valid whole number."
            );

            return;
        }

        if (duration < 1 || duration > 300) {

            JOptionPane.showMessageDialog(
                    this,
                    "Duration must be between 1 and 300 minutes."
            );

            return;
        }

        boolean success =
                examDAO.addExam(
                        title,
                        subject,
                        duration
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exam added successfully!"
            );

            clearFields();

            loadExams();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add exam.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // UPDATE EXAM
    // =========================

    private void updateExam() {

        int selectedRow =
                examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam first."
            );

            return;
        }

        int id =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );

        String title =
                titleField.getText().trim();

        String subject =
                subjectField.getText().trim();

        String durationText =
                durationField.getText().trim();

        if (
                title.isEmpty()
                        ||
                        subject.isEmpty()
                        ||
                        durationText.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        int duration;

        try {

            duration =
                    Integer.parseInt(
                            durationText
                    );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Duration must be a number."
            );

            return;
        }

        boolean success =
                examDAO.updateExam(
                        id,
                        title,
                        subject,
                        duration
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exam updated successfully!"
            );

            clearFields();

            loadExams();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update exam.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // DELETE EXAM
    // =========================

    private void deleteExam() {

        int selectedRow =
                examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam first."
            );

            return;
        }

        int id =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this exam?",
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

        boolean success =
                examDAO.deleteExam(id);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exam deleted successfully!"
            );

            clearFields();

            loadExams();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete exam.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================
    // LOAD SELECTED EXAM
    // =========================

    private void loadSelectedExam() {

        int selectedRow =
                examTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        titleField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString()
        );

        subjectField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString()
        );

        durationField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );
    }


    // =========================
    // CLEAR FIELDS
    // =========================

    private void clearFields() {

        titleField.setText("");

        subjectField.setText("");

        durationField.setText("");

        examTable.clearSelection();
    }
}
