package com.hrishabh.headlinehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<NewsArticle> newsArticleArrayList = new ArrayList<>();
    JSONArray articlesJSONArray = new JSONArray();
    private NewsViewAdapter adapter;
    String countryCode;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Get the intent that started this activity
        Intent intent = getIntent();
        countryCode = intent.getStringExtra("countryCode");
        category = intent.getStringExtra("category");

        populateList();
        if(newsArticleArrayList != null)
            adapter = new NewsViewAdapter(this);
        recyclerView.setAdapter(adapter);

    }
    private void populateList(){
        String url = OutsourcingMethods.urlCreator(countryCode, category);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            articlesJSONArray = response.getJSONArray("articles");
                            for(int i=0; i<articlesJSONArray.length(); i++){
                                JSONObject obj = articlesJSONArray.getJSONObject(i);
                                NewsArticle individualArticle = new NewsArticle();
                                OutsourcingMethods.convertJSONToNewsArticle(obj, individualArticle);
                                newsArticleArrayList.add(individualArticle);
                            }
                            adapter.updateNews(newsArticleArrayList);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", "Mozilla/5.0");
                        return headers;
                    }
        };

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onClick(View view) {
        TextView textViewURL = view.findViewById(R.id.url);
        Log.d("clickable URL", textViewURL.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(textViewURL.getText().toString()));
        startActivity(intent);
    }
}