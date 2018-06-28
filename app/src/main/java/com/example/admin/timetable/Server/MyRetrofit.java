package com.example.admin.timetable.Server;

import com.example.admin.timetable.Model.AniModel;
import com.example.admin.timetable.Model.CaptionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyRetrofit {
    @FormUrlEncoded
    @POST(Url.ANI_LIST)
    Call<List<AniModel>> getAni(
            @Field("w") int day
    );

    @FormUrlEncoded
    @POST(Url.CAP_LIST)
    Call<List<CaptionModel>> getCaption(
            @Field("i") int index
    );

    @FormUrlEncoded
    @POST(Url.OHLI_ANI_LIST)
    Call<List<AniModel>> getOHLIAni(
            @Field("w") int day
    );

    @FormUrlEncoded
    @POST(Url.OHLI_CAP_LIST)
    Call<List<CaptionModel>> getOHLICaption(
            @Field("i") int index
    );

    @FormUrlEncoded
    @POST(Url.OHLI_CAP_NOW)
    Call<List<CaptionModel>> getOHLIcrrntCaption(
            @Field("size") int size
    );


}
