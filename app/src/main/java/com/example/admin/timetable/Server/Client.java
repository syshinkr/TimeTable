package com.example.admin.timetable.Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Retrofit retrofit = null;
    private static Retrofit retrofitOHLI = null;

    public static Retrofit getInstance() {
        if(retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // 서버에서 날리는 json 응답을 변환
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getOHLIInstance() {
        if(retrofitOHLI == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.OHLI_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // 서버에서 날리는 json 응답을 변환
                    .build();
        }
        return retrofitOHLI;
    }
}
