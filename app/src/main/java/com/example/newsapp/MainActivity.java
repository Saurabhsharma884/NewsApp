package com.example.newsapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String img_url;
    String title;
    String desc;
    String URL = "https://gnews.io/api/v4/top-headlines?country=in&lang=en&token=";
    String API_KEY = "192e30720fae3e9854a83bfaac83a8bc";
    boolean NEWS_LOADED = false;
    ArrayList<News> NewsArticles = new ArrayList<>();
    RequestQueue requestQueue;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        checkConnectivity();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (!NEWS_LOADED) {
                    checkConnectivity();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "LOADED", Toast.LENGTH_SHORT);
                    toast.show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            makeRequest();
        } else {
            ImageView imageView = findViewById(R.id.went_wrong);
            imageView.setImageResource(R.drawable.went_wrong);
        }
    }

    private void makeRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL + API_KEY, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d("Saurabh", "onResponse: Everything is good " + response);
                try {
                    getDataFromResponse(response);
                    swipeRefreshLayout.setRefreshing(false);
                    NEWS_LOADED = true;
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
    }

    private void getDataFromResponse(JSONObject response) throws JSONException {
        JSONArray articles = response.getJSONArray("articles");
        NewsArticles = makeArticleList(articles);
//        Log.d("Saurabh", "onCreate: "+NewsArticles);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter newsAdapter = new NewsAdapter(NewsArticles);
        recyclerView.setAdapter(newsAdapter);
    }

    private ArrayList<News> makeArticleList(JSONArray articles) throws JSONException {

        for (int i = 0; i < 10; i++) {
            JSONObject news = articles.getJSONObject(i);
            title = news.getString("title");
            img_url = news.getString("image");
            desc = news.getString("description");
            NewsArticles.add(new News(title, img_url, desc));
//                    Log.d("Saurabh", "onResponse: " + title);
        }
        return NewsArticles;
    }
}