package com.example.fauzy.adminapp;

import java.io.Serializable;

public class DetailOrder implements Serializable {
    private String concatedMenu;
    private int qty = 1;
    private String jenis;
    public String getNamaMenu() {
        return concatedMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.concatedMenu = namaMenu;
    }

    public int getQty() {
        return qty;
    }

    public void incrementQty() {
        this.qty++;
    }

    public void concatMenuWithQty(){
        this.concatedMenu = this.concatedMenu + " " + "(" + this.qty + ")";
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
