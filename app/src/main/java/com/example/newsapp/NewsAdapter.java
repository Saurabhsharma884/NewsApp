package com.example.newsapp;

import android.content.Context;
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


    public NewsAdapter(Context context, ArrayList<News> mNewsArray) {
        super(context, 0, mNewsArray);
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

            }
        });


        return listItemView;
    }


//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        News news = mNewsArray.get(position);
//
//        String title = news.getTitle();
//        String imgUrl = news.getImg_url();
//        String desc = news.getDesc();
//
//        if (title.length() > 50)
//            title = title.substring(0, 50);
//        title = title + "...";
//        holder.getHeadingTextView().setText(title);
//
//
////        holder.getDescriptionTextView().setText(desc);
//
////        Log.d("Saurabh", "onBindViewHolder: " + title + desc);
//
//        Glide.with(holder.getNewsImageView().getContext())
//                .load(imgUrl)
//                .centerCrop()
//                .error(R.drawable.image_not_available)
//                .placeholder(R.drawable.hourglass)
//                .into(holder.getNewsImageView());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mNewsArray.size();
//    }

}
