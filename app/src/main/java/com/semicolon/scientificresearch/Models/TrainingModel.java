package com.semicolon.scientificresearch.Models;

import java.io.Serializable;

public class TrainingModel implements Serializable {
    private int course_id_pk;
    private String course_name;
    private String course_date;
    private String course_image;
    private String course_capacity;
    private int course_days;
    private String course_desc;
    private String course_funds;

    public TrainingModel() {
    }

    public TrainingModel(int course_id_pk, String course_name, String course_date, String course_image, String course_capacity, int course_days, String course_desc, String course_funds) {
        this.course_id_pk = course_id_pk;
        this.course_name = course_name;
        this.course_date = course_date;
        this.course_image = course_image;
        this.course_capacity = course_capacity;
        this.course_days = course_days;
        this.course_desc = course_desc;
        this.course_funds = course_funds;
    }

    public int getCourse_id_pk() {
        return course_id_pk;
    }

    public void setCourse_id_pk(int course_id_pk) {
        this.course_id_pk = course_id_pk;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_date() {
        return course_date;
    }

    public void setCourse_date(String course_date) {
        this.course_date = course_date;
    }

    public String getCourse_image() {
        return course_image;
    }

    public void setCourse_image(String course_image) {
        this.course_image = course_image;
    }

    public String getCourse_capacity() {
        return course_capacity;
    }

    public void setCourse_capacity(String course_capacity) {
        this.course_capacity = course_capacity;
    }

    public int getCourse_days() {
        return course_days;
    }

    public void setCourse_days(int course_days) {
        this.course_days = course_days;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public void setCourse_desc(String course_desc) {
        this.course_desc = course_desc;
    }

    public String getCourse_funds() {
        return course_funds;
    }

    public void setCourse_funds(String course_funds) {
        this.course_funds = course_funds;
    }
}
