package com.example.developer.newnote;

/**
 * Created by Developer on 4/18/2018.
 */

public class Note {
    private String title;
    private String desc;
    private int id;

    public Note(String title, String desc, int id) {
        this.title = title;
        this.desc = desc;
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}