package com.example.webapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private Context context;
    private List<GiphyResponse.Data> gifs;
    private GifClickListener gifClickListener;

    public GifAdapter(Context context, GifClickListener gifClickListener) {
        this.context = context;
        this.gifClickListener = gifClickListener;
    }

    // Method to set the gifs to display in RecyclerView
    public void setGifs(List<GiphyResponse.Data> gifs) {
        this.gifs = gifs;
        notifyDataSetChanged();
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gif, parent, false);
        return new GifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, int position) {
        GiphyResponse.Data gif = gifs.get(position);
        // Use Glide or Picasso to load the gif into the ImageView
        Glide.with(context)
                .load(gif.getUrl()) // Replace with appropriate field from your response model
                .into(holder.gifImageView);

        holder.favoriteButton.setOnClickListener(v -> gifClickListener.onGifClick(gif));
    }

    @Override
    public int getItemCount() {
        return gifs != null ? gifs.size() : 0;
    }

    public static class GifViewHolder extends RecyclerView.ViewHolder {
        ImageView gifImageView;
        Button favoriteButton;

        public GifViewHolder(View itemView) {
            super(itemView);
            gifImageView = itemView.findViewById(R.id.gifImageView);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }

    public interface GifClickListener {
        void onGifClick(GiphyResponse.Data gif);
    }
}
