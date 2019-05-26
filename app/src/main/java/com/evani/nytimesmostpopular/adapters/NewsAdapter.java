package com.evani.nytimesmostpopular.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evani.nytimesmostpopular.R;
import com.evani.nytimesmostpopular.models.MostPopular;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String TAG = "NewsAdapter";

    private final Context mContext;
    private List<MostPopular> articleList = new ArrayList<>();
    private List<String> imgUrls = new ArrayList<>();

    public NewsAdapter(Context mContext, List<MostPopular> articleList, List<String> imgUrls) {
        this.mContext = mContext;
        this.articleList = articleList;
        this.imgUrls = imgUrls;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        if (holder != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(imgUrls.get(position))
                    .into(holder.imageView);

            holder.title.setText(articleList.get(position).getTitle());
            holder.author.setText(articleList.get(position).getByline());
            holder.date.setText(articleList.get(position).getPublishedDate());
            holder.date.setText(articleList.get(position).getPublishedDate());

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked on: " + articleList.get(position).getUrl());


                    Toast.makeText(mContext, articleList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }



    @Override
    public int getItemCount() {
        return articleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView title;
        final TextView author;
        final TextView date;
        final LinearLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.articleImage);
            title = itemView.findViewById(R.id.articleTitle);
            author = itemView.findViewById(R.id.articleAuthor);
            date = itemView.findViewById(R.id.articleDate);
            parentLayout = itemView.findViewById(R.id.parent);


        }

    }

}
