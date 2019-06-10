package com.news.newstracker.model.repository;

import android.arch.lifecycle.MutableLiveData;

import com.news.newstracker.constants.GlobalVariables;
import com.news.newstracker.model.Article;
import com.news.newstracker.model.GetNewsFromRemoteServer;
import com.news.newstracker.retrofit.NewsApi;
import com.news.newstracker.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nibin Salim on 10/6/19.
 */
public class NewsRepositoryHandler {

    MutableLiveData<List<Article>> mLiveData;

    /*
     * calls the api to get the  data from  the server
     * *
     */
    public MutableLiveData<List<Article>> getLiveData() {

        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();
        }
        NewsApi serviceApi = RetrofitClient.getClient(GlobalVariables.BASE_URL).create(NewsApi.class);
        serviceApi.getAllNews(GlobalVariables.NEWS_URL)
                .enqueue(new Callback<GetNewsFromRemoteServer>() {
                    @Override
                    public void onResponse(Call<GetNewsFromRemoteServer> call, Response<GetNewsFromRemoteServer> response) {
                        if (response.isSuccessful()) {
                            GetNewsFromRemoteServer newsRes = response.body();
                            List<Article> articleList = newsRes.getArticles();
                            mLiveData.setValue(articleList);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNewsFromRemoteServer> call, Throwable t) {
                    }
                });
        return mLiveData;
    }
}
