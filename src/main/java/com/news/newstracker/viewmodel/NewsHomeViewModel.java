package com.news.newstracker.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.news.newstracker.model.Article;
import com.news.newstracker.model.repository.NewsRepositoryHandler;

import java.util.List;

/**
 * Created by Nibin Salim on 10/6/19.
 */
public class NewsHomeViewModel extends AndroidViewModel {

    NewsRepositoryHandler newsRepositoryHandler;

    public NewsHomeViewModel(@NonNull Application application) {
        super(application);
        newsRepositoryHandler = new NewsRepositoryHandler();
    }

    /**
     * calls the api to get the news
     *
     * @return the data  obtained from server
     */
    public LiveData<List<Article>> getAllArticle() {
        return newsRepositoryHandler.getLiveData();
    }


}
