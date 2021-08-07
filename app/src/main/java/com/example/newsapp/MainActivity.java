package com.example.newsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String img_url;
    String title;
    String desc;
    String URL = "https://gnews.io/api/v4/top-headlines?country=in&lang=en&token=";
    String API_KEY = "192e30720fae3e9854a83bfaac83a8bc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_card);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL + API_KEY, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d("Saurabh", "onResponse: Everything is good " + response);

                try {
                    JSONArray results = response.getJSONArray("articles");
                    JSONObject news1 = results.getJSONObject(0);
                    title = news1.getString("title");
                    img_url = news1.getString("image");
                    desc = news1.getString("description");
//                    Log.d("Saurabh", "onResponse: " + title);
                    setData(title, img_url, desc);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Saurabh", "onErrorResponse: Something Went Wrong " + error);

            }
        });
        requestQueue.add(jsonObjectRequest);


//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


    }

    private void setData(String title, String img_url, String desc) {

        ImageView imageView = findViewById(R.id.news_img);

        if (img_url == null) {
            imageView.setImageResource(R.drawable.image_not_available);
        } else {
            Glide.with(this)
                    .load(img_url)
                    .centerCrop()
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);
        }

        TextView headingView = findViewById(R.id.news_heading);
        if (title.length() > 50)
            title = title.substring(0, 50);

        headingView.setText(title + "...");

        TextView descriptionView = findViewById(R.id.news_description);
        descriptionView.setText(desc);

    }
}