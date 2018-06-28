package com.example.admin.timetable.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//최신 애니 자막 정보
public class OHLIAniModel {
    @SerializedName("i")
    private int index;

    @SerializedName("s")
    private String title;

    @SerializedName("t")
    private String time;

    @SerializedName("l")
    private String homeLink;

    @SerializedName("sd")
    private int sd;

    @SerializedName("ed")
    private int ed;

    @SerializedName("img")
    private String img;

    @SerializedName("e")
    private int episode;

    @SerializedName("b")
    private int before;

    @SerializedName("a")
    private int add_epi;

    @SerializedName("n")
    private List<NicknameModel> nicknames;

    public OHLIAniModel(int index, String title, String time, String homeLink, int sd, int ed, String img, int episode, int before, int add_epi, List<NicknameModel> nicknames) {
        this.index = index;
        this.title = title;
        this.time = time;
        this.homeLink = homeLink;
        this.sd = sd;
        this.ed = ed;
        this.img = img;
        this.episode = episode;
        this.before = before;
        this.add_epi = add_epi;
        this.nicknames = nicknames;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public int getSd() {
        return sd;
    }

    public int getEd() {
        return ed;
    }

    public String getImg() {
        return img;
    }

    public int getEpisode() {
        return episode;
    }

    public int getBefore() {
        return before;
    }

    public int getAdd_epi() {
        return add_epi;
    }

    public List<NicknameModel> getNicknames() {
        return nicknames;
    }
}
