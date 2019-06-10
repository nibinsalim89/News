package com.news.newstracker.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.news.newstracker.R;
import com.news.newstracker.adapters.NewsAdapter;
import com.news.newstracker.model.Article;
import com.news.newstracker.viewmodel.NewsHomeViewModel;

import java.util.List;

/**
 * Created by Nibin Salim on 10/6/19.
 * Description: Displays the news  with title in list
 */
public class NewsHomeActivity extends AppCompatActivity {

    //member variables
    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mRefreshNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();
        getAndShowNews();
    }

    /**
     * initialize the views
     */
    private void initViews() {

        mNewsRecyclerView = findViewById(R.id.recyclerView);
        mRefreshNews = findViewById(R.id.srl_news);
        mRefreshNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {
                getAndShowNews();

            }

        });

    }


    /*
    calls the api to get the  news feed
     */
    private void getAndShowNews() {

        if (!isOnline()) {
            mRefreshNews.setRefreshing(false);
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        NewsHomeViewModel activityViewModel = ViewModelProviders.of(this).get(NewsHomeViewModel.class);
        activityViewModel.getAllArticle().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                mRefreshNews.setRefreshing(false);
                mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(NewsHomeActivity.this));
                mNewsRecyclerView.setAdapter(new NewsAdapter(articles, NewsHomeActivity.this));
            }
        });
    }

    /**
     * isOnline method is used to check the network connectivity of the mobile device.
     */
    public boolean isOnline() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
