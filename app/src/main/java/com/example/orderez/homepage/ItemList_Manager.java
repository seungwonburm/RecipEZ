package com.example.orderez.homepage;

public class ItemList_Manager {
    String title;
    String count;
    String unit;
    String expDate;
    String memo;

    public ItemList_Manager(String title,String expDate, String count,String unit,String memo) {
        this.title = title;
        this.count = count;
        this.unit = unit;
        this.expDate = expDate;
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUnit() {return unit;}

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
