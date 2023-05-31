package com.example.alahamar.retrofit;

import com.example.alahamar.util.LenientGsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Api_Client {


    public static final String BASE_URL = "http://69.49.235.253:8067/api/";
    public static final String BASE_URL_IMAGE = "http://69.49.235.253:8067";
    public static final String BASE_URL_IMAGE1 = "http://69.49.235.253:8067";
    public static final String BASE_URL_IMAGE2 = "http://69.49.235.253:8067";
    public static final String BASE_URL_IMAGE3= "http://69.49.235.253:8067";

    private static Retrofit retrofit = null;
    private static Api api;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Api getClient() {
        if (api == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(LenientGsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Api api = retrofit.create(Api.class);

            return api;
        } else
            return api;



    }

}
