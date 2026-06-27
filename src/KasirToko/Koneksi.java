package KasirToko;

import KasirToko.Form.FormDashboard;
import KasirToko.Model.Barang;
import KasirToko.Model.Kategori;
import KasirToko.Model.Pelanggan;
import KasirToko.Model.Supplier;
import KasirToko.Model.Transaksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Koneksi {
    private static final String DATABASE_NAME = "db_kasirtoko";
    private static final String SERVER_URL = konfigurasi("kasirtoko.db.serverUrl", "KASIRTOKO_DB_SERVER_URL",
            "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=Asia/Jakarta");
    private static final String URL = konfigurasi("kasirtoko.db.url", "KASIRTOKO_DB_URL",
            "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useSSL=false&serverTimezone=Asia/Jakarta");
    private static final String USER = konfigurasi("kasirtoko.db.user", "KASIRTOKO_DB_USER", "root");
    private static final String PASSWORD = konfigurasi("kasirtoko.db.password", "KASIRTOKO_DB_PASSWORD", "");

    private static String konfigurasi(String propertyName, String envName, String defaultValue) {
        String propertyValue = System.getProperty(propertyName);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }

        String envValue = System.getenv(envName);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        return defaultValue;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        try (Connection serverConn = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
             Statement serverStmt = serverConn.createStatement()) {
            serverStmt.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + DATABASE_NAME + "`");
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal membuat database MySQL '" + DATABASE_NAME + "'. Periksa username/password MySQL.", ex);
        }

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS kategori (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nama VARCHAR(100) NOT NULL UNIQUE
                    )
                    """);
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS barang (
                        kode VARCHAR(30) PRIMARY KEY,
                        nama VARCHAR(150) NOT NULL,
                        kategori VARCHAR(100) NOT NULL,
                        stok INT NOT NULL DEFAULT 0,
                        harga DOUBLE NOT NULL DEFAULT 0
                    )
                    """);
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS pelanggan (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nama VARCHAR(150) NOT NULL,
                        telepon VARCHAR(50),
                        alamat VARCHAR(255)
                    )
                    """);
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS supplier (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nama VARCHAR(150) NOT NULL,
                        telepon VARCHAR(50),
                        alamat VARCHAR(255)
                    )
                    """);
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS transaksi (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nomor VARCHAR(30) NOT NULL UNIQUE,
                        barang_kode VARCHAR(30) NOT NULL,
                        nama_barang VARCHAR(150) NOT NULL,
                        jumlah INT NOT NULL,
                        harga DOUBLE NOT NULL,
                        total DOUBLE NOT NULL,
                        bayar DOUBLE NOT NULL,
                        kembalian DOUBLE NOT NULL,
                        tanggal TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    )
                    """);
            seedData(conn);
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal terhubung ke database MySQL. Pastikan database '" + DATABASE_NAME + "' bisa diakses dan MySQL Connector/J ada di classpath.", ex);
        }
    }

    private static void seedData(Connection conn) throws SQLException {
        if (isTableEmpty(conn, "kategori")) {
            tambahKategori(conn, "Makanan");
            tambahKategori(conn, "Minuman");
        }
        if (isTableEmpty(conn, "barang")) {
            tambahBarang(conn, new Barang("B001", "Roti", "Makanan", 20, 5000));
            tambahBarang(conn, new Barang("B002", "Air Mineral", "Minuman", 30, 3000));
        }
        if (isTableEmpty(conn, "pelanggan")) {
            tambahPelanggan(conn, new Pelanggan("Umum", "-", "-"));
        }
        if (isTableEmpty(conn, "supplier")) {
            tambahSupplier(conn, new Supplier("Supplier Utama", "08123456789", "Jakarta"));
        }
    }

    private static boolean isTableEmpty(Connection conn, String table) throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table)) {
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    public static List<Kategori> getKategori() {
        List<Kategori> data = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nama FROM kategori ORDER BY nama")) {
            while (rs.next()) {
                data.add(new Kategori(rs.getString("nama")));
            }
            return data;
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal mengambil data kategori.", ex);
        }
    }

    public static void tambahKategori(String nama) {
        try (Connection conn = getConnection()) {
            tambahKategori(conn, nama);
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menyimpan kategori.", ex);
        }
    }

    private static void tambahKategori(Connection conn, String nama) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO kategori (nama) VALUES (?)")) {
            ps.setString(1, nama);
            ps.executeUpdate();
        }
    }

    public static void hapusKategori(String nama) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM kategori WHERE nama = ?")) {
            ps.setString(1, nama);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menghapus kategori.", ex);
        }
    }

    public static List<Barang> getBarang() {
        List<Barang> data = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT kode, nama, kategori, stok, harga FROM barang ORDER BY kode")) {
            while (rs.next()) {
                data.add(new Barang(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getString("kategori"),
                        rs.getInt("stok"),
                        rs.getDouble("harga")
                ));
            }
            return data;
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal mengambil data barang.", ex);
        }
    }

    public static void tambahBarang(Barang barang) {
        try (Connection conn = getConnection()) {
            tambahBarang(conn, barang);
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menyimpan barang.", ex);
        }
    }

    private static void tambahBarang(Connection conn, Barang barang) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO barang (kode, nama, kategori, stok, harga)
                VALUES (?, ?, ?, ?, ?)
                """)) {
            ps.setString(1, barang.getKode());
            ps.setString(2, barang.getNama());
            ps.setString(3, barang.getKategori());
            ps.setInt(4, barang.getStok());
            ps.setDouble(5, barang.getHarga());
            ps.executeUpdate();
        }
    }

    public static void hapusBarang(String kode) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM barang WHERE kode = ?")) {
            ps.setString(1, kode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menghapus barang.", ex);
        }
    }

    public static Barang cariBarang(String kataKunci) {
        if (kataKunci == null || kataKunci.isBlank()) {
            return null;
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("""
                     SELECT kode, nama, kategori, stok, harga
                     FROM barang
                     WHERE kode = ? OR nama = ? OR nama LIKE ?
                     ORDER BY
                         CASE
                             WHEN kode = ? THEN 0
                             WHEN nama = ? THEN 1
                             ELSE 2
                         END,
                         nama
                     LIMIT 1
                     """)) {
            String keyword = kataKunci.trim();
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, keyword);
            ps.setString(5, keyword);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Barang(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getString("kategori"),
                        rs.getInt("stok"),
                        rs.getDouble("harga")
                );
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal mencari barang.", ex);
        }
    }

    public static List<Pelanggan> getPelanggan() {
        List<Pelanggan> data = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nama, telepon, alamat FROM pelanggan ORDER BY id")) {
            while (rs.next()) {
                data.add(new Pelanggan(rs.getString("nama"), rs.getString("telepon"), rs.getString("alamat")));
            }
            return data;
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal mengambil data pelanggan.", ex);
        }
    }

    public static void tambahPelanggan(Pelanggan pelanggan) {
        try (Connection conn = getConnection()) {
            tambahPelanggan(conn, pelanggan);
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menyimpan pelanggan.", ex);
        }
    }

    private static void tambahPelanggan(Connection conn, Pelanggan pelanggan) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO pelanggan (nama, telepon, alamat) VALUES (?, ?, ?)")) {
            ps.setString(1, pelanggan.getNama());
            ps.setString(2, pelanggan.getTelepon());
            ps.setString(3, pelanggan.getAlamat());
            ps.executeUpdate();
        }
    }

    public static void hapusPelanggan(Pelanggan pelanggan) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM pelanggan WHERE nama = ? AND telepon = ? AND alamat = ? LIMIT 1")) {
            ps.setString(1, pelanggan.getNama());
            ps.setString(2, pelanggan.getTelepon());
            ps.setString(3, pelanggan.getAlamat());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menghapus pelanggan.", ex);
        }
    }

    public static List<Supplier> getSupplier() {
        List<Supplier> data = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nama, telepon, alamat FROM supplier ORDER BY id")) {
            while (rs.next()) {
                data.add(new Supplier(rs.getString("nama"), rs.getString("telepon"), rs.getString("alamat")));
            }
            return data;
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal mengambil data supplier.", ex);
        }
    }

    public static void tambahSupplier(Supplier supplier) {
        try (Connection conn = getConnection()) {
            tambahSupplier(conn, supplier);
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menyimpan supplier.", ex);
        }
    }

    private static void tambahSupplier(Connection conn, Supplier supplier) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO supplier (nama, telepon, alamat) VALUES (?, ?, ?)")) {
            ps.setString(1, supplier.getNama());
            ps.setString(2, supplier.getTelepon());
            ps.setString(3, supplier.getAlamat());
            ps.executeUpdate();
        }
    }

    public static void hapusSupplier(Supplier supplier) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM supplier WHERE nama = ? AND telepon = ? AND alamat = ? LIMIT 1")) {
            ps.setString(1, supplier.getNama());
            ps.setString(2, supplier.getTelepon());
            ps.setString(3, supplier.getAlamat());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menghapus supplier.", ex);
        }
    }

    public static List<Transaksi> getTransaksi() {
        List<Transaksi> data = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nomor, nama_barang, jumlah, harga FROM transaksi ORDER BY id DESC")) {
            while (rs.next()) {
                data.add(new Transaksi(
                        rs.getString("nomor"),
                        rs.getString("nama_barang"),
                        rs.getInt("jumlah"),
                        rs.getDouble("harga")
                ));
            }
            return data;
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal mengambil data transaksi.", ex);
        }
    }

    public static String nomorTransaksiBaru() {
        try (Connection conn = getConnection()) {
            return nomorTransaksiBaru(conn);
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal membuat nomor transaksi.", ex);
        }
    }

    private static String nomorTransaksiBaru(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT COUNT(*) + 1 FROM transaksi")) {
            rs.next();
            return "TRX" + String.format("%04d", rs.getInt(1));
        }
    }

    public static Transaksi simpanTransaksi(Barang barang, int jumlah, double bayar) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                Barang barangTerbaru = cariBarang(conn, barang.getKode());
                if (barangTerbaru == null) {
                    throw new IllegalStateException("Barang tidak ditemukan.");
                }
                if (jumlah <= 0 || jumlah > barangTerbaru.getStok()) {
                    throw new IllegalStateException("Jumlah tidak valid atau stok kurang.");
                }

                double total = jumlah * barangTerbaru.getHarga();
                if (bayar < total) {
                    throw new IllegalStateException("Uang bayar kurang.");
                }

                Transaksi transaksi = new Transaksi(
                        nomorTransaksiBaru(conn),
                        barangTerbaru.getNama(),
                        jumlah,
                        barangTerbaru.getHarga()
                );

                try (PreparedStatement stok = conn.prepareStatement("UPDATE barang SET stok = stok - ? WHERE kode = ? AND stok >= ?")) {
                    stok.setInt(1, jumlah);
                    stok.setString(2, barangTerbaru.getKode());
                    stok.setInt(3, jumlah);
                    if (stok.executeUpdate() == 0) {
                        throw new IllegalStateException("Stok barang tidak cukup.");
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement("""
                        INSERT INTO transaksi (nomor, barang_kode, nama_barang, jumlah, harga, total, bayar, kembalian)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                        """)) {
                    ps.setString(1, transaksi.getNomor());
                    ps.setString(2, barangTerbaru.getKode());
                    ps.setString(3, transaksi.getNamaBarang());
                    ps.setInt(4, transaksi.getJumlah());
                    ps.setDouble(5, transaksi.getHarga());
                    ps.setDouble(6, transaksi.getTotal());
                    ps.setDouble(7, bayar);
                    ps.setDouble(8, bayar - transaksi.getTotal());
                    ps.executeUpdate();
                }

                conn.commit();
                return transaksi;
            } catch (RuntimeException | SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Gagal menyimpan transaksi.", ex);
        }
    }

    private static Barang cariBarang(Connection conn, String kode) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT kode, nama, kategori, stok, harga FROM barang WHERE kode = ?")) {
            ps.setString(1, kode);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Barang(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getString("kategori"),
                        rs.getInt("stok"),
                        rs.getDouble("harga")
                );
            }
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Look and feel default digunakan.");
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                initDatabase();
                new FormDashboard().setVisible(true);
            } catch (IllegalStateException ex) {
                String detail = ex.getCause() == null ? "" : "\n\nDetail: " + ex.getCause().getMessage();
                String pesan = ex.getMessage() + detail;
                if (java.awt.GraphicsEnvironment.isHeadless()) {
                    System.err.println(pesan);
                    return;
                }
                JOptionPane.showMessageDialog(null, pesan);
            }
        });
    }
}
