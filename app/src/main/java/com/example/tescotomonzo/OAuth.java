package com.example.tescotomonzo;

import android.app.DownloadManager;


import com.example.tescotomonzo.DateDeserializer;
import com.example.tescotomonzo.DeclineReasonDeserializer;
import com.example.tescotomonzo.MonzoAPI;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Date;

public class OAuth {

    public static String MONZO_API_URL = "https://api.monzo.com/";
    public static String USER_AUTH_URL = "https://auth.getmondo.co.uk/";

    private static Retrofit.Builder createBaseBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        gsonBuilder.registerTypeAdapter(DeclineReasonDeserializer.class, new DeclineReasonDeserializer());

        return new Retrofit.Builder()
                .baseUrl(MONZO_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));
    }


    public static MonzoAPI createService() {
        //create String
        return createBaseBuilder().build().create(MonzoAPI.class);
    }

    public static MonzoAPI createService(final String accesstoken) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization",
                                "Bearer " + accesstoken)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).build();

        return createBaseBuilder().client(client).build().create(MonzoAPI.class);
    }
}