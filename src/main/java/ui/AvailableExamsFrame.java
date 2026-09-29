package com.onlineexam.ui;

import com.onlineexam.dao.ExamDAO;
import com.onlineexam.model.Exam;
import com.onlineexam.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AvailableExamsFrame extends JFrame {

    private User user;

    private JTable examTable;

    private DefaultTableModel tableModel;

    private ExamDAO examDAO;

    public AvailableExamsFrame(User user) {

        this.user = user;

        examDAO = new ExamDAO();

        setTitle("Available Examinations");

        setSize(900, 600);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadExams();

        setVisible(true);
    }

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

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "AVAILABLE EXAMINATIONS",
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
        // TABLE
        // =========================

        String[] columns = {
                "ID",
                "Exam Title",
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

        examTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        examTable.setRowHeight(30);

        JScrollPane scrollPane =
                new JScrollPane(examTable);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON
        // =========================

        JButton startButton =
                new JButton("Start Exam");

        startButton.setPreferredSize(
                new Dimension(150, 40)
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(startButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // EVENT
        // =========================

        startButton.addActionListener(
                e -> startExam()
        );
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
                            exam.getDurationMinutes()
                                    + " minutes",
                            exam.getTotalQuestions()
                    }
            );
        }
    }

    // =========================
    // START EXAM
    // =========================

    private void startExam() {

        int selectedRow =
                examTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an exam first.",
                    "Select Exam",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int examId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );

        String title =
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();

        String subject =
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();

        int duration =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        3
                                )
                                .toString()
                                .replace(
                                        " minutes",
                                        ""
                                )
                );

        int totalQuestions =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        4
                                )
                                .toString()
                );

        if (totalQuestions == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "This exam does not have any questions yet.",
                    "Exam Not Available",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Exam exam =
                new Exam(
                        examId,
                        title,
                        subject,
                        duration,
                        totalQuestions
                );

        new ExamInstructionsFrame(
                user,
                exam
        );

        dispose();
    }
}
