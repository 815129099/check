package com.example.demo.domain;

import java.io.Serializable;

public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String stuNumber;
    private String checkName;
    private String checkType;
    private String checkState;
    private String checkPeople;
    private String updateTime;
    private int duty;
    private int discipline;
    private int work;
    private int team;
    private String item;
    private int deduct;
    private int total;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getDuty() {
        return duty;
    }

    public void setDuty(int duty) {
        this.duty = duty;
    }

    public int getDiscipline() {
        return discipline;
    }

    public void setDiscipline(int discipline) {
        this.discipline = discipline;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getDeduct() {
        return deduct;
    }

    public void setDeduct(int deduct) {
        this.deduct = deduct;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
