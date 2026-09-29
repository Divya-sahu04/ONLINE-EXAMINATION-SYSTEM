package com.onlineexam.ui;

import com.onlineexam.dao.UserDAO;
import com.onlineexam.model.User;
import com.onlineexam.util.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentManagementFrame extends JFrame {

    private UserDAO userDAO;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentManagementFrame() {

        userDAO = new UserDAO();

        setTitle("Manage Students");
        setSize(800, 500);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadStudents();

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
                        "REGISTERED STUDENTS",
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
                "Student ID",
                "Name",
                "Email",
                "Role"
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

        studentTable =
                new JTable(tableModel);

        UIStyle.styleTable(
                studentTable
        );

        studentTable.setRowHeight(30);

        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        studentTable
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

        JButton closeButton =
                new JButton("Close");

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                refreshButton
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
                e -> loadStudents()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        add(mainPanel);
    }

    private void loadStudents() {

        tableModel.setRowCount(0);

        List<User> students =
                userDAO.getAllStudents();

        for (User student : students) {

            tableModel.addRow(
                    new Object[]{
                            student.getId(),
                            student.getName(),
                            student.getEmail(),
                            student.getRole()
                    }
            );
        }

        if (students.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No registered students found.",
                    "Students",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
