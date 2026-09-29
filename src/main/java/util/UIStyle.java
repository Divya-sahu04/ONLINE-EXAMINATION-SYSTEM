package com.onlineexam.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIStyle {

    private UIStyle() {
        // Prevent object creation
    }

    public static final Font TITLE_FONT =
            new Font(
                    "Arial",
                    Font.BOLD,
                    24
            );

    public static final Font SUBTITLE_FONT =
            new Font(
                    "Arial",
                    Font.BOLD,
                    18
            );

    public static final Font NORMAL_FONT =
            new Font(
                    "Arial",
                    Font.PLAIN,
                    14
            );

    public static final Font BUTTON_FONT =
            new Font(
                    "Arial",
                    Font.BOLD,
                    14
            );


    public static void styleButton(
            JButton button
    ) {

        button.setFont(
                BUTTON_FONT
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        150,
                        38
                )
        );
    }


    public static void styleTitle(
            JLabel label
    ) {

        label.setFont(
                TITLE_FONT
        );

        label.setHorizontalAlignment(
                SwingConstants.CENTER
        );
    }


    public static void styleTable(
            JTable table
    ) {

        table.setFont(
                NORMAL_FONT
        );

        table.setRowHeight(30);

        table.getTableHeader()
                .setFont(
                        BUTTON_FONT
                );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
    }


    public static JPanel createMainPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        return panel;
    }
}
