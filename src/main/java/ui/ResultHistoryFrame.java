package com.onlineexam.ui;

import com.onlineexam.dao.ResultDAO;
import com.onlineexam.model.User;
import com.onlineexam.util.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class ResultHistoryFrame extends JFrame {

    private User user;

    private ResultDAO resultDAO;

    private JTable resultTable;

    private DefaultTableModel tableModel;

    public ResultHistoryFrame(User user) {

        this.user = user;

        resultDAO =
                new ResultDAO();

        setTitle("My Results");

        setSize(
                1000,
                600
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadResults();

        setVisible(true);
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
                user,
                resultId
        );
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
                        "MY EXAM RESULTS",
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

        String[] columns = {
                "Result ID",
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

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        JButton backButton =
                new JButton(
                        "Back to Dashboard"
                );
        JButton viewAnswersButton =
                new JButton(
                        "View Answers"
                );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                refreshButton
        );

        buttonPanel.add(
                backButton
        );
        buttonPanel.add(
                viewAnswersButton
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        refreshButton.addActionListener(
                e -> loadResults()
        );
        viewAnswersButton.addActionListener(
                e -> viewSelectedAnswers()
        );

        backButton.addActionListener(
                e -> {

                    new StudentDashboard(user);

                    dispose();
                }
        );

        add(mainPanel);
    }

    private void loadResults() {

        tableModel.setRowCount(0);

        List<Object[]> results =
                resultDAO.getResultHistory(
                        user.getId()
                );

        for (Object[] row : results) {

            Timestamp completedAt =
                    (Timestamp) row[6];

            tableModel.addRow(
                    new Object[]{
                            row[0],
                            row[1],
                            row[2],
                            row[3],
                            row[4],
                            String.format(
                                    "%.2f%%",
                                    (Double) row[5]
                            ),
                            completedAt
                    }
            );
        }

        if (results.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "You have not completed any exams yet.",
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}