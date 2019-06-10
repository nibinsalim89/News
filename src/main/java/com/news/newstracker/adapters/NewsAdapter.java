package com.news.newstracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.newstracker.R;
import com.news.newstracker.constants.GlobalVariables;
import com.news.newstracker.model.Article;
import com.news.newstracker.views.NewsInDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nibin Salim on 10/6/19.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<Article> mArticleList;
    private Context mContext;

    public NewsAdapter(List<Article> articleList, Context context) {
        this.mArticleList = articleList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        //sets the values to the list items
        holder.title.setText(mArticleList.get(position).getTitle());
        holder.date.setText(mArticleList.get(position).getPublishedAt().replace("T", " ").replace("Z", ""));
        String photoUrl = mArticleList.get(position).getUrlToImage();
        Picasso.get().load(photoUrl).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        ImageView imageView;

        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.image);

            //handles the  item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent newsIntent = new Intent(mContext, NewsInDetailActivity.class);
                    newsIntent.putExtra(GlobalVariables.DATE, mArticleList.get(position).getPublishedAt());
                    newsIntent.putExtra(GlobalVariables.DESCRIPTION, mArticleList.get(position).getDescription());
                    newsIntent.putExtra(GlobalVariables.TITLE, mArticleList.get(position).getTitle());
                    newsIntent.putExtra(GlobalVariables.IMAGE_URL, mArticleList.get(position).getUrlToImage());
                    mContext.startActivity(newsIntent);


                }
            });
        }
    }

}
