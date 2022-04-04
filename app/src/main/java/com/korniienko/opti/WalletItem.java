package com.korniienko.opti;

public class WalletItem {
    int id;
    String name;
    int sum;
    String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public WalletItem(int id, String name, int sum, String date) {
        this.id = id;
        this.name = name;
        this.sum = sum;
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
