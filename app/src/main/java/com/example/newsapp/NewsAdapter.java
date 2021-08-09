package com.example.newsapp;

import android.graphics.drawable.Drawable;
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

    private ArrayList<News> mNewsArray;

    public NewsAdapter(ArrayList<News> mNewsArray) {
        this.mNewsArray = mNewsArray;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = mNewsArray.get(position);

        String title = news.getTitle();
        String imgUrl = news.getImg_url();
        String desc = news.getDesc();

        if (title.length() > 50)
            title = title.substring(0, 50);
        title = title + "...";
        holder.getHeadingTextView().setText(title);


        holder.getDescriptionTextView().setText(desc);

//        Log.d("Saurabh", "onBindViewHolder: " + title + desc);

        if (imgUrl == null) {
            holder.getNewsImageView().setImageResource(R.drawable.image_not_available);
        } else {
            Glide.with(holder.getNewsImageView().getContext())
                    .load(imgUrl)
                    .centerCrop()
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(Drawable.createFromPath("https://tenor.com/6IOp.gif"))
                    .into(holder.getNewsImageView());
        }
    }

    @Override
    public int getItemCount() {
        return mNewsArray.size();
    }

}
