package com.news.newstracker.retrofit;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Nibin Salim on 10/6/19.
 *
 * Description Class to  handle the retrofit
 */

public final class RetrofitClient {

    public static Retrofit getClient(String baseUrl){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}