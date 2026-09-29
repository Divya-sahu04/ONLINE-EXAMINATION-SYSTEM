package com.onlineexam;

import com.onlineexam.ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                LoginFrame::new
        );
    }
}