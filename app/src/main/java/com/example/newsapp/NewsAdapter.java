package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {


    private final Context context;

    public NewsAdapter(Context context, ArrayList<News> mNewsArray) {
        super(context, 0, mNewsArray);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_card, parent, false);

        News currentNews = getItem(position);

        String title = currentNews.getmTitle();
        String imgUrl = currentNews.getmImg_url();
        String desc = currentNews.getmDesc();
        Uri newsUri = Uri.parse(currentNews.getmNews_url());

        TextView headingText = listItemView.findViewById(R.id.news_heading);
        TextView descText = listItemView.findViewById(R.id.news_description);
        ImageView newsImg = listItemView.findViewById(R.id.news_img);

        if (title.length() > 50)
            title = title.substring(0, 50);
        title = title + "...";
        headingText.setText(title);
        descText.setText(desc);

        Glide.with(getContext())
                .load(imgUrl)
                .centerCrop()
                .error(R.drawable.image_not_available)
                .placeholder(R.drawable.hourglass)
                .into(newsImg);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                context.startActivity(intent);

            }
        });

        return listItemView;
    }
}
