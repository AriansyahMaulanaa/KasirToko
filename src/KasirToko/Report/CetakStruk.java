/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KasirToko.Report;

import KasirToko.Model.Transaksi;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Ariansyah
 */
public class CetakStruk {
    public static String buatStruk(Transaksi transaksi, double bayar) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        double kembalian = bayar - transaksi.getTotal();

        return "KASIR TOKO\n"
                + "------------------------------\n"
                + "No Transaksi : " + transaksi.getNomor() + "\n"
                + "Barang       : " + transaksi.getNamaBarang() + "\n"
                + "Jumlah       : " + transaksi.getJumlah() + "\n"
                + "Harga        : " + rupiah.format(transaksi.getHarga()) + "\n"
                + "Total        : " + rupiah.format(transaksi.getTotal()) + "\n"
                + "Bayar        : " + rupiah.format(bayar) + "\n"
                + "Kembalian    : " + rupiah.format(kembalian) + "\n"
                + "------------------------------\n"
                + "Terima kasih";
    }
}
