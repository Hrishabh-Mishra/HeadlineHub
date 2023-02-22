package com.hrishabh.headlinehub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    public NewsViewAdapter(View.OnClickListener listener) {
        this.listener = listener;
    }

    List<NewsArticle> newsArticleArrayList = Collections.emptyList();
    View.OnClickListener listener;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creating view from the item_news.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        view.setOnClickListener(listener);
        //Everytime a new viewHolder is created, it is returned from here.
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        //According to the position we can get the current item from the ArrayList
        NewsArticle currentItem = newsArticleArrayList.get(position);
        holder.title.setText(currentItem.getTitle());
        Picasso.get().load(currentItem.getUrlToImage()).into(holder.urlToImage);
        holder.description.setText(currentItem.getDescription());
        holder.url.setText(currentItem.getUrl());
        holder.author.setText("Article by: "+currentItem.getAuthor());
    }

    @Override
    public int getItemCount() {
        return newsArticleArrayList.size();
    }

    public void updateNews(List<NewsArticle> updatedNewsArticleArrayList){
        this.newsArticleArrayList = updatedNewsArticleArrayList;
        notifyDataSetChanged();
    }
}

class NewsViewHolder extends RecyclerView.ViewHolder {

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    TextView title = itemView.findViewById(R.id.title);
    TextView author = itemView.findViewById(R.id.author);
    TextView description = itemView.findViewById(R.id.description);
    TextView url = itemView.findViewById(R.id.url);
    ImageView urlToImage = itemView.findViewById(R.id.url_to_image);
}
