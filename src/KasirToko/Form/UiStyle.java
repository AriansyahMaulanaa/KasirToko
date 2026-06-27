package KasirToko.Form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

final class UiStyle {
    static final Color PAGE = new Color(232, 232, 232);
    static final Color PANEL = new Color(246, 246, 246);
    static final Color HEADER = new Color(78, 78, 78);
    static final Color HEADER_DARK = new Color(55, 55, 55);
    static final Color LINE = new Color(188, 188, 188);
    static final Color TEXT = new Color(45, 45, 45);
    static final Color MUTED = new Color(100, 100, 100);

    private UiStyle() {
    }

    static Border padding(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    static JPanel header(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout(8, 2));
        panel.setBackground(HEADER);
        panel.setBorder(padding(12, 18, 12, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setForeground(new Color(230, 230, 230));
        subtitleLabel.setFont(subtitleLabel.getFont().deriveFont(12f));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(subtitleLabel, BorderLayout.SOUTH);
        return panel;
    }

    static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setBorder(padding(0, 0, 8, 0));
        return label;
    }

    static JLabel formLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    static JPanel contentPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE),
                padding(12, 14, 12, 14)
        ));
        return panel;
    }

    static JPanel actionBar() {
        JPanel panel = new JPanel();
        panel.setBackground(PAGE);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, LINE));
        return panel;
    }

    static JButton button(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(116, 30));
        return button;
    }

    static void styleTable(JTable table) {
        table.setRowHeight(26);
        table.setGridColor(new Color(215, 215, 215));
        table.setSelectionBackground(new Color(210, 210, 210));
        table.setSelectionForeground(TEXT);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 28));
        table.getTableHeader().setBackground(new Color(215, 215, 215));
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
    }

    static void setPage(Component component) {
        component.setBackground(PAGE);
    }
}
