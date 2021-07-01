package main;

import java.time.LocalDate;
import java.util.Date;

public class TableClass {
    private int number;
    private int id;
    private String task;
    private int day;
    private LocalDate date;
    private String category;
    private String status;

    public TableClass(int number, int id, String task, int day, LocalDate date, String category, String status) {
        this.number = number;
        this.id = id;
        this.task = task;
        this.day = day;
        this.date = date;
        this.category = category;
        this.status = status;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate localDate) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
