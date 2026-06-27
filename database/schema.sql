CREATE DATABASE IF NOT EXISTS db_KasirToko;
USE db_KasirToko;

CREATE TABLE IF NOT EXISTS kategori (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS barang (
    kode VARCHAR(30) PRIMARY KEY,
    nama VARCHAR(150) NOT NULL,
    kategori VARCHAR(100) NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    harga DOUBLE NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS pelanggan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(150) NOT NULL,
    telepon VARCHAR(50),
    alamat VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS supplier (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(150) NOT NULL,
    telepon VARCHAR(50),
    alamat VARCHAR(255)
);

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
);

INSERT IGNORE INTO kategori (nama) VALUES
('Makanan'),
('Minuman');

INSERT IGNORE INTO barang (kode, nama, kategori, stok, harga) VALUES
('B001', 'Roti', 'Makanan', 20, 5000),
('B002', 'Air Mineral', 'Minuman', 30, 3000);

INSERT INTO pelanggan (nama, telepon, alamat)
SELECT 'Umum', '-', '-'
WHERE NOT EXISTS (SELECT 1 FROM pelanggan WHERE nama = 'Umum' AND telepon = '-' AND alamat = '-');

INSERT INTO supplier (nama, telepon, alamat)
SELECT 'Supplier Utama', '08123456789', 'Jakarta'
WHERE NOT EXISTS (SELECT 1 FROM supplier WHERE nama = 'Supplier Utama' AND telepon = '08123456789' AND alamat = 'Jakarta');
