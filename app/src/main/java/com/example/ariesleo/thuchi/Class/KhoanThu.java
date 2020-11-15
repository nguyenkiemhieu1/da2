package com.example.ariesleo.thuchi.Class;

/**
 * Created by Admin on 5/10/2017.
 */

public class KhoanThu {
    int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String ten;
    Integer sotien;
    String loai;
    String ngay;
    String ghichu;


    public String getTen() {

        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getSotien() {
        return sotien;
    }

    public void setSotien(Integer sotien) {
        this.sotien = sotien;
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

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public KhoanThu(int id, String ten, Integer sotien, String loai, String ngay, String ghichu) {

        this.id=id;
        this.ten = ten;
        this.sotien = sotien;
        this.loai = loai;
        this.ngay = ngay;
        this.ghichu = ghichu;
    }
    public KhoanThu()
    {

    }
}
