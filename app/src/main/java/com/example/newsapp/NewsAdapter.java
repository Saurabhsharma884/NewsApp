package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    private final Context context;
    private final ArrayList<News> mNewsArray;

    public NewsAdapter(Context context, ArrayList<News> mNewsArray) {
        this.mNewsArray = mNewsArray;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView headingTextView;
        private final TextView descriptionTextView;
        private final ImageView newsImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.news_img);
            headingTextView = itemView.findViewById(R.id.news_heading);
            descriptionTextView = itemView.findViewById(R.id.news_description);
        }

        public TextView getHeadingTextView() {
            return headingTextView;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public ImageView getNewsImageView() {
            return newsImageView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        News curNews = mNewsArray.get(position);

        String title = curNews.getmTitle();
        String imgUrl = curNews.getmImg_url();
        String desc = curNews.getmDesc();

        if (title.length() > 50)
            title = title.substring(0, 50);
        title = title + "...";
        holder.getHeadingTextView().setText(title);
        holder.getDescriptionTextView().setText(desc);

        Glide.with(context)
                .load(imgUrl)
                .centerCrop()
                .error(R.drawable.image_not_available)
                .placeholder(R.drawable.hourglass)
                .into(holder.getNewsImageView());

    }

    @Override
    public int getItemCount() {
        return mNewsArray.size();
    }
}
