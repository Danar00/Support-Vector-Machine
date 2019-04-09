package com.company;

public class Data {

    private double beratBadan;
    private double tinggiBadan;
    private int kelas;

    public Data(double beratBadan, double tinggiBadan, int kelas) {
        this.beratBadan = beratBadan;
        this.tinggiBadan = tinggiBadan;
        this.kelas = kelas;
    }

    public int getKelas() {
        return kelas;
    }

    public void setKelas(int kelas) {
        this.kelas = kelas;
    }

    public double getBeratBadan() {
        return beratBadan;
    }

    public void setBeratBadan(double beratBadan) {
        this.beratBadan = beratBadan;
    }

    public double getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(double tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }
}
