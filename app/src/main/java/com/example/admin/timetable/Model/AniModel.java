package com.example.admin.timetable.Model;

import com.google.gson.annotations.SerializedName;

public class AniModel {
    @SerializedName("i")
    private int id;

    @SerializedName("s")
    private String title;

    @SerializedName("t")
    private String time;

    @SerializedName("g")
    private String genre;

    @SerializedName("l")
    private String homeLink;

    @SerializedName("a")
    private boolean absent; //a boolean : true 진행 | false 금주결방 -> false일 경우 제목앞에 [결방] 을 표시한다. (boolean Broadcast)

    /** Start Day
     * 애니메이션 시작일 :
     * 20131017 -> 2013년 10월 17일 |
     * 단 날짜형식에 맞지않는 99999999 같은것이 들어갈 수 잇으며 8자리의 숫자형태로 되어있다.
     * 일요일 ~ 토요일 까지의 애니메이션중 현재 날짜보다 미래인 경우 제목앞에 월일을 표시한다. 현재날짜 20120101 값 20131017 - > [10/17
     */
    @SerializedName("sd")
    private String sd;

    /** End Day
     * 애니메이션 종료일 : sd와 포맷같음
     * 일요일 ~ 토요일 까지의 애니메이션중 현재 날짜보다 과거인 경우 제목앞에 [完]을 표시한다.
     */
    @SerializedName("ed")
    private String ed;

    private boolean isFavorite;

    public AniModel(int id, String title, String time, String genre, String homeLink, boolean absent, String sd, String ed) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.genre = genre;
        this.homeLink = homeLink;
        this.absent = absent;
        this.sd = sd.substring(4, 6) + "월 " + sd.substring(6, 8) + "일";
        this.ed = ed.substring(4, 6) + "월 " + ed.substring(6, 8) + "일";
        this.isFavorite = false;
    }

    public AniModel(int id, String title, String time, String genre, String homeLink, boolean absent, String sd, String ed, boolean isFavorite) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.genre = genre;
        this.homeLink = homeLink;
        this.absent = absent;
        this.sd = sd;
        this.ed = ed;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getGenre() {
        return genre;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public boolean isAbsent() {
        return absent;
    }

    public String getSd() {
        return sd;
    }

    public String getEd() {
        return ed;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
