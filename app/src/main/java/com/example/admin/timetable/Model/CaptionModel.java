package com.example.admin.timetable.Model;

import com.google.gson.annotations.SerializedName;

public class CaptionModel {
    /**
     *  s : 화수 : 00000 -> 0000.0 : 즉 00012 면 1.2화 : 5자리 숫자 4자리 정수 1자리 소수점 : 주의 : 5자리 숫자 외 BD, EX, PIC, OVA, OAD 값이 올 수 있다.
     * 정리 : 5자리 숫자 혹은 BD, EX, PIC, OVA, OAD
     * 숫자 : 4자리 정수 마지막 1자리는 소수점 마지막 값이 0일경우 소수점 생략.
     **/
    @SerializedName("s")
    String episode;

    @SerializedName("d")
    String date; //d : 갱신날짜 : 20131201093938 -> 2013년 12월 01일 09시 39분 38초

    @SerializedName("a")
    String address="about:blank"; // a : 주소 :  자막주소 -> https:// 는 생략되어있음. | 자막 제작자가 입력하지 않은 경우 주소가 "" 빈스트링으로 올 수 있다.

    @SerializedName("n")
    String name; // n : 자막 제작자 이름

    public CaptionModel(String episode, String date, String address, String name) {
        this.episode = episode;
        this.date = date.substring(4, 6) + "월 " + date.substring(6, 8) + "일";
        this.address = address;
        this.name = name;
    }

    public String getEpisode() {
        return episode;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}