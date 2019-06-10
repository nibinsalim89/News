package com.news.newstracker.retrofit;

import com.news.newstracker.model.GetNewsFromRemoteServer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Nibin Salim on 10/6/19.
 *
 * Description :Interface to  handle the api calls
 */
public interface NewsApi {

    @GET
    Call<GetNewsFromRemoteServer> getAllNews(@Url String urlString);

}