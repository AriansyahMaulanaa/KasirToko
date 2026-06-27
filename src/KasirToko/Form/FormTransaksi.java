/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package KasirToko.Form;

import KasirToko.Koneksi;
import KasirToko.Model.Barang;
import KasirToko.Model.Transaksi;
import KasirToko.Report.CetakStruk;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Ariansyah
 */
public class FormTransaksi extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormTransaksi.class.getName());

    /**
     * Creates new form FormTransaksi
     */
    public FormTransaksi() {
        initComponents();
        buatTampilan();
    }

    private JTextField txtKode;
    private JTextField txtNama;
    private JTextField txtHarga;
    private JTextField txtStok;
    private JTextField txtJumlah;
    private JTextField txtTotal;
    private JTextField txtBayar;
    private JTextField txtKembalian;
    private JTextArea txtStruk;
    private JLabel lblTotalBesar;
    private Barang barangDipilih;
    private Transaksi transaksiTerakhir;

    private void buatTampilan() {
        setTitle("Transaksi Penjualan");
        setSize(980, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtKode = new JTextField(14);
        txtNama = new JTextField(22);
        txtHarga = new JTextField(14);
        txtStok = new JTextField(8);
        txtJumlah = new JTextField(8);
        txtTotal = new JTextField(14);
        txtBayar = new JTextField(18);
        txtKembalian = new JTextField(18);
        txtStruk = new JTextArea();

        txtNama.setEditable(false);
        txtHarga.setEditable(false);
        txtStok.setEditable(false);
        txtTotal.setEditable(false);
        txtKembalian.setEditable(false);
        txtStruk.setEditable(false);
        txtStruk.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        lblTotalBesar = new JLabel("0,00", javax.swing.SwingConstants.RIGHT);
        lblTotalBesar.setOpaque(true);
        lblTotalBesar.setBackground(Color.WHITE);
        lblTotalBesar.setForeground(Color.BLACK);
        lblTotalBesar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(UiStyle.LINE),
                UiStyle.padding(14, 18, 14, 18)
        ));
        lblTotalBesar.setPreferredSize(new Dimension(0, 76));
        lblTotalBesar.setFont(lblTotalBesar.getFont().deriveFont(Font.BOLD, 46f));

        JButton btnCari = UiStyle.button("Cari");
        JButton btnHitung = UiStyle.button("Hitung");
        JButton btnSimpan = UiStyle.button("Simpan");
        JButton btnBaru = UiStyle.button("Baru");

        JPanel formBarang = UiStyle.contentPanel();
        formBarang.setLayout(new GridBagLayout());
        formBarang.setPreferredSize(new Dimension(560, 248));
        JPanel ringkasan = UiStyle.contentPanel();
        ringkasan.setLayout(new GridBagLayout());
        ringkasan.setPreferredSize(new Dimension(460, 248));
        JPanel konten = new JPanel(new BorderLayout(14, 14));
        konten.setBackground(UiStyle.PAGE);
        konten.setBorder(UiStyle.padding(14, 18, 14, 18));
        JPanel atas = new JPanel(new GridBagLayout());
        atas.setOpaque(false);
        JScrollPane scrollStruk = new JScrollPane(txtStruk);
        JPanel kodePanel = new JPanel(new BorderLayout(8, 0));
        JPanel jumlahPanel = new JPanel(new BorderLayout(8, 0));
        JPanel bayarPanel = new JPanel(new BorderLayout(8, 0));
        JPanel tombol = UiStyle.actionBar();
        tombol.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 8));

        kodePanel.add(txtKode, BorderLayout.CENTER);
        kodePanel.add(btnCari, BorderLayout.EAST);
        jumlahPanel.add(txtJumlah, BorderLayout.CENTER);
        jumlahPanel.add(btnHitung, BorderLayout.EAST);
        bayarPanel.add(txtBayar, BorderLayout.CENTER);
        bayarPanel.add(btnSimpan, BorderLayout.EAST);

        tambahBaris(formBarang, 0, "Kode / Nama Barang", kodePanel);
        tambahBaris(formBarang, 1, "Nama Barang", txtNama);
        tambahBaris(formBarang, 2, "Harga", txtHarga);
        tambahBaris(formBarang, 3, "Stok", txtStok);
        tambahBaris(formBarang, 4, "Jumlah", jumlahPanel);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(0, 0, 10, 0);
        ringkasan.add(UiStyle.sectionTitle("Total Transaksi"), c);

        c.gridy = 1;
        ringkasan.add(lblTotalBesar, c);
        tambahBaris(ringkasan, 2, "Total", txtTotal);
        tambahBaris(ringkasan, 3, "Bayar", bayarPanel);
        tambahBaris(ringkasan, 4, "Kembalian", txtKembalian);

        GridBagConstraints atasC = new GridBagConstraints();
        atasC.gridx = 0;
        atasC.gridy = 0;
        atasC.fill = GridBagConstraints.BOTH;
        atasC.weightx = 0.58;
        atasC.insets = new Insets(0, 0, 0, 14);
        atas.add(formBarang, atasC);

        atasC.gridx = 1;
        atasC.weightx = 0.42;
        atasC.insets = new Insets(0, 0, 0, 0);
        atas.add(ringkasan, atasC);
        tombol.add(btnBaru);


        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(UiStyle.header("Transaksi Penjualan", "Input barang, hitung total, simpan transaksi, dan lihat struk"), BorderLayout.NORTH);
        konten.add(atas, BorderLayout.NORTH);
        konten.add(scrollStruk, BorderLayout.CENTER);
        getContentPane().add(konten, BorderLayout.CENTER);
        getContentPane().add(tombol, BorderLayout.SOUTH);

        btnCari.addActionListener(e -> cariBarang());
        btnHitung.addActionListener(e -> hitungTotal());
        btnSimpan.addActionListener(e -> simpanTransaksi());
        btnBaru.addActionListener(e -> kosongkan());
    }

    private void tambahBaris(JPanel panel, int row, String label, java.awt.Component field) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(4, 0, 4, 10);
        panel.add(UiStyle.formLabel(label), c);

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 0);
        panel.add(field, c);
    }

    private void cariBarang() {
        barangDipilih = Koneksi.cariBarang(txtKode.getText().trim());
        if (barangDipilih == null) {
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan.");
            return;
        }

        txtNama.setText(barangDipilih.getNama());
        txtKode.setText(barangDipilih.getKode());
        txtHarga.setText(String.valueOf(barangDipilih.getHarga()));
        txtStok.setText(String.valueOf(barangDipilih.getStok()));
    }

    private void hitungTotal() {
        if (barangDipilih == null) {
            JOptionPane.showMessageDialog(this, "Cari barang terlebih dahulu.");
            return;
        }

        try {
            int jumlah = Integer.parseInt(txtJumlah.getText().trim());
            if (jumlah <= 0 || jumlah > barangDipilih.getStok()) {
                JOptionPane.showMessageDialog(this, "Jumlah tidak valid atau stok kurang.");
                return;
            }
            double total = jumlah * barangDipilih.getHarga();
            txtTotal.setText(String.valueOf(total));
            lblTotalBesar.setText(String.format("%,.2f", total));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus angka.");
        }
    }

    private void simpanTransaksi() {
        try {
            if (barangDipilih == null || txtTotal.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hitung total terlebih dahulu.");
                return;
            }

            int jumlah = Integer.parseInt(txtJumlah.getText().trim());
            double bayar = Double.parseDouble(txtBayar.getText().trim());
            double total = Double.parseDouble(txtTotal.getText().trim());

            if (bayar < total) {
                JOptionPane.showMessageDialog(this, "Uang bayar kurang.");
                return;
            }

            transaksiTerakhir = Koneksi.simpanTransaksi(barangDipilih, jumlah, bayar);
            barangDipilih = Koneksi.cariBarang(txtKode.getText().trim());

            txtKembalian.setText(String.valueOf(bayar - total));
            txtStok.setText(String.valueOf(barangDipilih.getStok()));
            txtStruk.setText(CetakStruk.buatStruk(transaksiTerakhir, bayar));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah dan bayar harus angka.");
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void kosongkan() {
        barangDipilih = null;
        transaksiTerakhir = null;
        txtKode.setText("");
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        txtJumlah.setText("");
        txtTotal.setText("");
        txtBayar.setText("");
        txtKembalian.setText("");
        txtStruk.setText("");
        lblTotalBesar.setText("0,00");
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
        java.awt.EventQueue.invokeLater(() -> new FormTransaksi().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
