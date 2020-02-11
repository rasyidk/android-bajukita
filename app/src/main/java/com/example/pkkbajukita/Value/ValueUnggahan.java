package com.example.pkkbajukita.Value;

public class ValueUnggahan {

    String id_barang;
    String nama_barang;
    String ket_barang;
    String lokasi_barang;
    String img_barang;
    String no_wa;




    public ValueUnggahan(String id_barang, String nama_barang, String ket_barang, String lokasi_barang, String img_barang, String no_wa) {
        this.id_barang = id_barang;
        this.nama_barang = nama_barang;
        this.ket_barang = ket_barang;
        this.lokasi_barang = lokasi_barang;
        this.img_barang = img_barang;
        this.no_wa = no_wa;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getKet_barang() {
        return ket_barang;
    }

    public void setKet_barang(String ket_barang) {
        this.ket_barang = ket_barang;
    }

    public String getLokasi_barang() {
        return lokasi_barang;
    }

    public void setLokasi_barang(String lokasi_barang) {
        this.lokasi_barang = lokasi_barang;
    }

    public String getImg_barang() {
        return img_barang;
    }

    public void setImg_barang(String img_barang) {
        this.img_barang = img_barang;
    }

    public String getNo_wa() {
        return no_wa;
    }

    public void setNo_wa(String no_wa) {
        this.no_wa = no_wa;
    }

}
