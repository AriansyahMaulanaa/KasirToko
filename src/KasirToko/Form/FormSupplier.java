/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package KasirToko.Form;

import KasirToko.Koneksi;
import KasirToko.Model.Supplier;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ariansyah
 */
public class FormSupplier extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormSupplier.class.getName());

    /**
     * Creates new form FormSupplier
     */
    public FormSupplier() {
        initComponents();
        buatTampilan();
        tampilData();
    }

    private DefaultTableModel model;
    private JTextField txtNama;
    private JTextField txtTelepon;
    private JTextField txtAlamat;

    private void buatTampilan() {
        setTitle("Data Supplier");
        setSize(680, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtNama = new JTextField();
        txtTelepon = new JTextField();
        txtAlamat = new JTextField();
        JButton btnSimpan = UiStyle.button("Simpan");
        JButton btnHapus = UiStyle.button("Hapus");

        JPanel input = UiStyle.contentPanel();
        input.setLayout(new GridBagLayout());
        JPanel tombol = UiStyle.actionBar();
        tombol.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        tambahBaris(input, 0, "Nama", txtNama);
        tambahBaris(input, 1, "Telepon", txtTelepon);
        tambahBaris(input, 2, "Alamat", txtAlamat);
        tombol.add(btnHapus);
        tombol.add(btnSimpan);

        model = new DefaultTableModel(new String[]{"Nama", "Telepon", "Alamat"}, 0);
        JTable tabel = new JTable(model);
        UiStyle.styleTable(tabel);

        JPanel konten = new JPanel(new BorderLayout(14, 14));
        konten.setBackground(UiStyle.PAGE);
        konten.setBorder(UiStyle.padding(14, 18, 14, 18));
        konten.add(input, BorderLayout.NORTH);
        konten.add(new JScrollPane(tabel), BorderLayout.CENTER);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(UiStyle.header("Master Data Supplier", "Nama, telepon, dan alamat supplier"), BorderLayout.NORTH);
        getContentPane().add(konten, BorderLayout.CENTER);
        getContentPane().add(tombol, BorderLayout.SOUTH);

        btnSimpan.addActionListener(e -> {
            try {
                Koneksi.tambahSupplier(new Supplier(
                        txtNama.getText().trim(),
                        txtTelepon.getText().trim(),
                        txtAlamat.getText().trim()
                ));
                txtNama.setText("");
                txtTelepon.setText("");
                txtAlamat.setText("");
                tampilData();
            } catch (IllegalStateException ex) {
                tampilError(ex);
            }
        });

        btnHapus.addActionListener(e -> {
            int baris = tabel.getSelectedRow();
            if (baris >= 0) {
                try {
                    Koneksi.hapusSupplier(new Supplier(
                            model.getValueAt(baris, 0).toString(),
                            model.getValueAt(baris, 1).toString(),
                            model.getValueAt(baris, 2).toString()
                    ));
                    tampilData();
                } catch (IllegalStateException ex) {
                    tampilError(ex);
                }
            }
        });
    }

    private void tambahBaris(JPanel panel, int row, String label, java.awt.Component field) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(4, 0, 4, 12);
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 0);
        panel.add(field, c);
    }

    private void tampilData() {
        try {
            model.setRowCount(0);
            for (Supplier supplier : Koneksi.getSupplier()) {
                model.addRow(new Object[]{
                    supplier.getNama(),
                    supplier.getTelepon(),
                    supplier.getAlamat()
                });
            }
        } catch (IllegalStateException ex) {
            tampilError(ex);
        }
    }

    private void tampilError(IllegalStateException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormSupplier().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
