package com.example.newsapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    String news_url;
    String title;
    String desc;
    String TAG = "Saurabh";

    String URL_BASE = "https://gnews.io/api/v4/top-headlines?";
    String API_KEY = "192e30720fae3e9854a83bfaac83a8bc";

    boolean NEWS_LOADED = false;
    int NETWORK_ERROR = 0;
    int VOLLEY_ERROR = 1;

    SwipeRefreshLayout swipeRefreshLayout;
    RequestQueue requestQueue;
    ArrayList<News> newsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mtoolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mtoolbar);


        requestQueue = Volley.newRequestQueue(this);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        checkConnectivity();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkConnectivity();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_setting) {
            Toast.makeText(this, "setting clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    void makeRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getRequestUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d("Saurabh", "onResponse: Everything is good " + response);
                try {
                    makeListFromResponse(response);
                    NEWS_LOADED = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Saurabh", "onErrorResponse: Something Went Wrong " + error);
                alertDialog(VOLLEY_ERROR);
            }
        });

        requestQueue.add(jsonObjectRequest);
        swipeRefreshLayout.setRefreshing(false);
    }

    private String getRequestUrl() {
        return URL_BASE + "country=" + SettingActivity.getCountry() + "&lang=" + SettingActivity.getLanguage() + "&token=" + API_KEY;
    }

    void makeListFromResponse(JSONObject response) throws JSONException {
        JSONArray articles = response.getJSONArray("articles");
        for (int i = 0; i < 10; i++) {
            JSONObject news = articles.getJSONObject(i);
            title = news.getString("title");
            img_url = news.getString("image");
            desc = news.getString("description");
            news_url = news.getString("url");
            newsArrayList.add(new News(title, img_url, desc, news_url));
        }

        RecyclerView recyclerView = findViewById(R.id.news_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter newsAdapter = new NewsAdapter(this, newsArrayList);
        recyclerView.setAdapter(newsAdapter);

    }

    public void checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            if (!NEWS_LOADED)
                makeRequest();
            else {
                Toast.makeText(this, "LOADED", Toast.LENGTH_SHORT).show();
            }
        } else {
            alertDialog(NETWORK_ERROR);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    void alertDialog(int error) {
        AlertFragment alert = new AlertFragment(error);
        alert.show(getSupportFragmentManager(), "alert");
    }

}

