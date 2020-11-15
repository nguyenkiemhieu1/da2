package com.example.ariesleo.thuchi.Class;

/**
 * Created by Aries Leo on 5/11/2017.
 */

public class KhoanChi {
    int id;
    String ten;
    Integer tien;
    String loai;
    String ngay;
    String ghichi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getTien() {
        return tien;
    }

    public void setTien(Integer tien) {
        this.tien = tien;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGhichi() {
        return ghichi;
    }

    public void setGhichi(String ghichi) {
        this.ghichi = ghichi;
    }

    public KhoanChi(int id, String ten, Integer tien, String loai, String ngay, String ghichi) {

        this.id = id;
        this.ten = ten;
        this.tien = tien;
        this.loai = loai;
        this.ngay = ngay;
        this.ghichi = ghichi;
    }

    public KhoanChi() {
    }
}

