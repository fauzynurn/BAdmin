package com.example.fauzy.adminapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserOrder implements Serializable{
    private String nama;
    private String totalHarga;
    private List<DetailOrder> detailOrder = new ArrayList<>();

    public UserOrder(){}

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public List<DetailOrder> getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(List<DetailOrder> detailOrder) {
        this.detailOrder = detailOrder;
    }

//    class DetailOrder{
//        private String namaMenu;
//        public String getNamaMenu() {
//            return namaMenu;
//        }
//
//        public void setNamaMenu(String namaMenu) {
//            this.namaMenu = namaMenu;
//        }
//    }
}
