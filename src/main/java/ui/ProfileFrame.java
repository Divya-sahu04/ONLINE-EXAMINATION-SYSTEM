package com.onlineexam.ui;

import com.onlineexam.dao.ProfileDAO;
import com.onlineexam.model.User;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {

    private User user;

    private ProfileDAO profileDAO;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField roleField;
    private JTextField idField;

    private JButton updateNameButton;
    private JButton changePasswordButton;
    private JButton backButton;


    public ProfileFrame(User user) {

        this.user = user;

        profileDAO =
                new ProfileDAO();

        setTitle("My Profile");

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
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        40,
                        25,
                        40
                )
        );


        JLabel title =
                new JLabel(
                        "MY PROFILE",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );


        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                15
                        )
                );


        // ID
        formPanel.add(
                new JLabel("User ID:")
        );

        idField =
                new JTextField(
                        String.valueOf(
                                user.getId()
                        )
                );

        idField.setEditable(false);

        formPanel.add(idField);


        // Name
        formPanel.add(
                new JLabel("Name:")
        );

        nameField =
                new JTextField(
                        user.getName()
                );

        formPanel.add(nameField);


        // Email
        formPanel.add(
                new JLabel("Email:")
        );

        emailField =
                new JTextField(
                        user.getEmail()
                );

        emailField.setEditable(false);

        formPanel.add(emailField);


        // Role
        formPanel.add(
                new JLabel("Role:")
        );

        roleField =
                new JTextField(
                        user.getRole()
                );

        roleField.setEditable(false);

        formPanel.add(roleField);


        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );


        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );


        updateNameButton =
                new JButton(
                        "Update Name"
                );

        changePasswordButton =
                new JButton(
                        "Change Password"
                );

        backButton =
                new JButton(
                        "Back"
                );


        buttonPanel.add(
                updateNameButton
        );

        buttonPanel.add(
                changePasswordButton
        );

        buttonPanel.add(
                backButton
        );


        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        updateNameButton.addActionListener(
                e -> updateName()
        );

        changePasswordButton.addActionListener(
                e -> changePassword()
        );

        backButton.addActionListener(
                e -> dispose()
        );


        add(mainPanel);
    }


    private void updateName() {

        String name =
                nameField
                        .getText()
                        .trim();


        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            nameField.requestFocus();

            return;
        }


        if (name.length() < 2) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name must contain at least 2 characters.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            nameField.requestFocus();

            return;
        }


        boolean updated =
                profileDAO.updateName(
                        user.getId(),
                        name
                );


        if (updated) {

            user.setName(name);

            JOptionPane.showMessageDialog(
                    this,
                    "Name updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update your name.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void changePassword() {

        JPasswordField currentPasswordField =
                new JPasswordField();

        JPasswordField newPasswordField =
                new JPasswordField();

        JPasswordField confirmPasswordField =
                new JPasswordField();


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                10,
                                10
                        )
                );


        panel.add(
                new JLabel(
                        "Current Password:"
                )
        );

        panel.add(
                currentPasswordField
        );


        panel.add(
                new JLabel(
                        "New Password:"
                )
        );

        panel.add(
                newPasswordField
        );


        panel.add(
                new JLabel(
                        "Confirm Password:"
                )
        );

        panel.add(
                confirmPasswordField
        );


        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (option != JOptionPane.OK_OPTION) {
            return;
        }


        String currentPassword =
                new String(
                        currentPasswordField
                                .getPassword()
                );

        String newPassword =
                new String(
                        newPasswordField
                                .getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );


        if (currentPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your current password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (newPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a new password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (newPassword.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "New password must contain at least 6 characters.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (!newPassword.equals(
                confirmPassword
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "New passwords do not match.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        boolean updated =
                profileDAO.updatePassword(
                        user.getId(),
                        currentPassword,
                        newPassword
                );


        if (updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password changed successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Current password is incorrect.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
