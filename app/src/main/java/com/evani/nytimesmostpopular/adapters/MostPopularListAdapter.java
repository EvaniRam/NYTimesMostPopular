package com.evani.nytimesmostpopular.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.evani.nytimesmostpopular.R;
import com.evani.nytimesmostpopular.models.MostPopular;

import java.util.ArrayList;
import java.util.List;

public class MostPopularListAdapter extends RecyclerView.Adapter<MostPopularListAdapter.ViewHolder> {

    private final List<MostPopular> items = new ArrayList<>();

    private final LayoutInflater inflater;
    private final RequestManager imageLoader;

    private List<MostPopular> articleList = new ArrayList<>();
    @Nullable
    private final OnItemClickListener clickListener;


    public MostPopularListAdapter(Context context, @Nullable OnItemClickListener clickListener) {
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;

        final RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.ic_image_placeholder)
                .fallback(R.drawable.ic_image_placeholder)
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.list_news_item, parent, false),clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceItems(@NonNull List<MostPopular> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull MostPopular newsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewLeft;
        private final TextView textViewNewsSummary;
        private final TextView textViewBy;
        private final TextView textViewLocation;
        private final TextView textViewDate;

        ViewHolder(@NonNull View itemView, @Nullable final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items.get(position));
                }
            });

            textViewNewsSummary = itemView.findViewById(R.id.articleTitle);
            imageViewLeft = itemView.findViewById(R.id.articleImage);
            textViewBy = itemView.findViewById(R.id.articleAuthor);
            textViewLocation = itemView.findViewById(R.id.location);
            textViewDate = itemView.findViewById(R.id.articleDate);
        }

        void bind(MostPopular mostPopular) {
            imageLoader.load(mostPopular.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageViewLeft);
            textViewNewsSummary.setText(mostPopular.getTitle());
            textViewBy.setText(mostPopular.getByline());
            textViewLocation.setText(mostPopular.getSection());
            textViewDate.setText(mostPopular.getPublishedDate());
        }
    }

}