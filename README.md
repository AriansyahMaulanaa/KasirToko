# 🧾 KasirToko — Aplikasi Kasir Berbasis Java Desktop

**Tugas Akhir Mata Kuliah Pemrograman II**  
Dosen Pengampu: *[isi nama dosen]*  
Disusun oleh: **Ariansyah Maulana**

---

## 📌 Deskripsi Proyek

**KasirToko** adalah aplikasi kasir desktop yang dibangun menggunakan **Java** dengan IDE **Apache NetBeans**. Aplikasi ini dirancang untuk membantu pencatatan transaksi penjualan pada toko secara sederhana dan efisien.

Proyek ini dibuat sebagai **tugas akhir mata kuliah Pemrograman II** yang bertujuan mengimplementasikan konsep pemrograman berorientasi objek (OOP), koneksi database, serta pembuatan antarmuka grafis (GUI) menggunakan Java Swing.

---

## 🚀 Fitur Utama

- **Manajemen Barang** — Tambah, edit, hapus, dan cari data barang
- **Manajemen Kategori** — Kelola kategori barang
- **Manajemen Pelanggan** — Data pelanggan toko
- **Manajemen Supplier** — Data pemasok barang
- **Transaksi Penjualan** — Proses kasir dengan perhitungan otomatis
- **Laporan** — Cetak laporan dan struk transaksi
- **Dashboard** — Tampilan ringkasan data toko

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Keterangan |
|-----------|-----------|
| **Java** | Bahasa pemrograman utama |
| **Java Swing** | GUI / antarmuka pengguna |
| **MySQL** | Database penyimpanan data |
| **NetBeans** | IDE pengembangan |
| **JasperReports** | Cetak laporan & struk |

---

## 📁 Struktur Proyek

```
KasirToko/
├── src/
│   └── KasirToko/
│       ├── Form/           # Form GUI (Swing)
│       ├── Model/           # Class model (Barang, Kategori, dll)
│       ├── Report/          # Cetak laporan & struk
│       ├── Koneksi.java     # Koneksi database
│       └── Main.java        # Entry point aplikasi
├── database/
│   └── schema.sql           # Skema database MySQL
├── lib/                     # Library / JDBC driver
├── nbproject/               # Konfigurasi proyek NetBeans
└── build.xml                # Build script Ant
```

---

## ⚙️ Cara Menjalankan

1. **Clone repository ini**
   ```bash
   git clone https://github.com/AriansyahMaulanaa/KasirToko.git
   ```

2. **Import database**
   - Buka MySQL (XAMPP / Laragon / standalone)
   - Jalankan file `database/schema.sql`

3. **Konfigurasi koneksi database**
   - Buka `src/KasirToko/Koneksi.java`, sesuaikan `host`, `user`, dan `password` MySQL

4. **Buka di NetBeans**
   - File → Open Project → Pilih folder `KasirToko`
   - Jalankan (F6 / Run)

---

## 📸 Tampilan Aplikasi

> *(Screenshot bisa ditambahkan di sini)*

---

## 📄 Lisensi

Proyek ini dibuat untuk keperluan akademik. Silakan digunakan sebagai referensi pembelajaran.

---

> **Universitas** — *[isi nama universitas]*  
> **Fakultas Ilmu Komputer** — *Program Studi Ilmu Komputer*
