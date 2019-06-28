package com.project.syshinkr.timetable.model;

import com.google.gson.annotations.SerializedName;

class NicknameModel {
    @SerializedName("i")
    private int index;

    @SerializedName("s")
    private int nickName;

    public NicknameModel(int index, int nickName) {
        this.index = index;
        this.nickName = nickName;
    }
}
