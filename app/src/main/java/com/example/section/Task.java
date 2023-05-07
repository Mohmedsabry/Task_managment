package com.example.section;

public class Task {
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", checked=" + checked +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    private int id;
    private int checked;
    private String title;
    private String date;
    private String desc;

    public Task(String title, String date, String desc) {
        this.title = title;
        this.date = date;
        this.desc = desc;
    }

    public Task(int id, int checked, String title, String date, String desc) {
        this.id = id;
        this.checked = checked;
        this.title = title;
        this.date = date;
        this.desc = desc;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
