package com.onlineexam.ui;

import com.onlineexam.dao.ResultDAO;
import com.onlineexam.util.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class ResultManagementFrame extends JFrame {

    private ResultDAO resultDAO;

    private JTable resultTable;
    private DefaultTableModel tableModel;

    public ResultManagementFrame() {

        resultDAO =
                new ResultDAO();

        setTitle("All Exam Results");

        setSize(1200, 600);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadResults();

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

        // =========================
        // TITLE
        // =========================

        JLabel title =
                new JLabel(
                        "ALL EXAM RESULTS",
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
                "Result ID",
                "Student",
                "Email",
                "Exam",
                "Subject",
                "Score",
                "Total",
                "Percentage",
                "Completed At"
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

        resultTable =
                new JTable(tableModel);

        UIStyle.styleTable(
                resultTable
        );

        resultTable.setRowHeight(30);

        resultTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        resultTable
                );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTONS
        // =========================

        JButton refreshButton =
                new JButton("Refresh");

        JButton viewAnswersButton =
                new JButton("View Answers");

        JButton closeButton =
                new JButton("Close");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                viewAnswersButton
        );

        buttonPanel.add(
                closeButton
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> loadResults()
        );

        viewAnswersButton.addActionListener(
                e -> viewSelectedAnswers()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        add(mainPanel);
    }

    private void loadResults() {

        tableModel.setRowCount(0);

        List<Object[]> results =
                resultDAO.getAllResults();

        for (Object[] row : results) {

            Timestamp completedAt =
                    (Timestamp) row[8];

            tableModel.addRow(
                    new Object[]{
                            row[0],
                            row[1],
                            row[2],
                            row[3],
                            row[4],
                            row[5],
                            row[6],
                            String.format(
                                    "%.2f%%",
                                    (Double) row[7]
                            ),
                            completedAt
                    }
            );
        }

        if (results.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No exam results found.",
                    "Results",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    private void viewSelectedAnswers() {

        int selectedRow =
                resultTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a result first.",
                    "Select Result",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int resultId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );

        new AnswerReviewFrame(
                null,
                resultId
        );
    }
}
