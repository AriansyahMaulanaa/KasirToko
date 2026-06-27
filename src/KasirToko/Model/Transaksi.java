/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KasirToko.Model;

/**
 *
 * @author Ariansyah
 */
public class Transaksi {
    private String nomor;
    private String namaBarang;
    private int jumlah;
    private double harga;
    private double total;

    public Transaksi(String nomor, String namaBarang, int jumlah, double harga) {
        this.nomor = nomor;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.harga = harga;
        this.total = jumlah * harga;
    }

    public String getNomor() {
        return nomor;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public double getTotal() {
        return total;
    }
}
