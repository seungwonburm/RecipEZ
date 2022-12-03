package com.example.orderez.homepage;

public class ItemList_Manager {
    String title; //아이템 이름
    String count; //얼마나 남았는지 표기하기 위한 정수
    String startDate, endDate; //시작하는 날짜, 끝나는 날짜

    public ItemList_Manager(String title, String count, String startDate, String endDate) {
        this.title = title;
        this.count = count;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
