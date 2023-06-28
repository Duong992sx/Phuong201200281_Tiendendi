package com.example.phuong201200281_tiendendi;

import java.io.Serializable;

public class ThuChi  {
  private int id;
    private String tennguoi;
    private  String ngaythang;
    private  String noidung;
    private  int sotien;
    private  boolean phuchi;

    public ThuChi(String tennguoi, String ngaythang,String noidung, int sotien, boolean phuchi) {
        this.tennguoi = tennguoi;
        this.ngaythang = ngaythang;
        this.noidung = noidung;
        this.sotien = sotien;
        this.phuchi = phuchi;
    }

    public ThuChi(int id, String tennguoi, String ngaythang,String noidung, int sotien, int phuchi) {
        this.id = id;
        this.tennguoi = tennguoi;
        this.ngaythang = ngaythang;
        this.sotien = sotien;
        if(phuchi == 1){
            this.phuchi = true;
        }else {
            this.phuchi = false;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTennguoi() {
        return tennguoi;
    }

    public void setTenkhoan(String tenkhoan) {
        this.tennguoi = tennguoi;
    }

    public String getNgaythang() {
        return ngaythang;
    }

    public void setNgaythang(String ngaythang) {
        this.ngaythang = ngaythang;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public boolean isPhuchi() {
        return phuchi;
    }

    public void setPhuchi(boolean phuchi) {
        this.phuchi = phuchi;
    }
}