package com.project.syshinkr.timetable.model;

import com.google.gson.annotations.SerializedName;

public class CrrntOHLICaptionModel {
    @SerializedName("d")
    private String date;

    @SerializedName("t")
    private String title;

    @SerializedName("s")
    private float episode;

    @SerializedName("n")
    private String name;

    @SerializedName("a")
    private String link;

    @SerializedName("p")
    private int point;

    public CrrntOHLICaptionModel(String date, String title, float episode, String name, String link, int point) {
        this.date = date;
        this.title = title;
        this.episode = episode;
        this.name = name;
        this.link = link;
        this.point = point;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getEpisode() {
        return episode;
    }

    public void setEpisode(float episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
