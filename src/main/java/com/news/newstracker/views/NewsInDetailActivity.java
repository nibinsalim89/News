package com.news.newstracker.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.newstracker.R;
import com.news.newstracker.constants.GlobalVariables;
import com.squareup.picasso.Picasso;

/**
 * Created by Nibin Salim on 10/6/19.
 * Description: Displays the news  with title and description
 */
public class NewsInDetailActivity extends AppCompatActivity {

    //member variables
    private TextView mDateView, mDescriptionView, mTitleView;
    private ImageView mNewsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_in_detail);

        initViews();
        getIntentValues();
    }

    /**
     * initialize the views
     */
    private void initViews() {

        mDateView = findViewById(R.id.tv_date);
        mDescriptionView = findViewById(R.id.tv_description);
        mTitleView = findViewById(R.id.tv_title);
        mNewsImage = findViewById(R.id.iv_newsImage);
        getSupportActionBar().setTitle(getString(R.string.toolbar_title_news_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * the  the values passed through  intent and
     * sets the same to the views  present in the activity
     */
    private void getIntentValues() {

        String date = getIntent().getStringExtra(GlobalVariables.DATE).replace("T", " ")
                .replace("Z", "");
        String title = getIntent().getStringExtra(GlobalVariables.TITLE);
        String description = getIntent().getStringExtra(GlobalVariables.DESCRIPTION);
        String newsImageUrl = getIntent().getStringExtra(GlobalVariables.IMAGE_URL);
        mDateView.setText(date);
        mTitleView.setText(title);
        mDescriptionView.setText(description);
        Picasso.get().load(newsImageUrl).into(mNewsImage);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

