/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KasirToko.Model;

/**
 *
 * @author Ariansyah
 */
public class Barang {
    private String kode;
    private String nama;
    private String kategori;
    private int stok;
    private double harga;

    public Barang(String kode, String nama, String kategori, int stok, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.kategori = kategori;
        this.stok = stok;
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void kurangiStok(int jumlah) {
        stok -= jumlah;
    }

    @Override
    public String toString() {
        return kode + " - " + nama;
    }
}
