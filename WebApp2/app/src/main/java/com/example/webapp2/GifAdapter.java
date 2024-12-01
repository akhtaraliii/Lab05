package com.example.webapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private Context context;
    private List<GiphyResponse.Data> gifs;
    private SharedPreferences sharedPreferences;
    private Set<String> favorites;

    public GifAdapter(Context context, List<GiphyResponse.Data> gifs) {
        this.context = context;
        this.gifs = gifs;
        // Initialize SharedPreferences to store favorite GIFs
        this.sharedPreferences = context.getSharedPreferences("FavoritesPrefs", Context.MODE_PRIVATE);
        this.favorites = sharedPreferences.getStringSet("favorite_gifs", new HashSet<>());
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the gif_item layout to create the view for each item
        View view = LayoutInflater.from(context).inflate(R.layout.item_gif, parent, false);
        return new GifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, int position) {
        // Get the current GIF object
        GiphyResponse.Data gif = gifs.get(position);
        String gifUrl = gif.getImages().getOriginal().getUrl(); // URL for the GIF

        // Load the GIF into the ImageView using Glide
        Glide.with(context).load(gifUrl).into(holder.gifImage);

        // Set the initial state of the favorite button
        if (favorites.contains(gifUrl)) {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_filled); // Show filled heart if it's a favorite
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_outline); // Show empty heart if it's not a favorite
        }

        // Set an OnClickListener for the favorite button
        holder.favoriteButton.setOnClickListener(v -> {
            boolean isFavorite = favorites.contains(gifUrl);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (isFavorite) {
                // Remove from favorites
                favorites.remove(gifUrl);
                editor.putStringSet("favorite_gifs", favorites);
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                holder.favoriteButton.setImageResource(R.drawable.ic_heart_outline); // Change button icon to empty heart
            } else {
                // Add to favorites
                favorites.add(gifUrl);
                editor.putStringSet("favorite_gifs", favorites);
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                holder.favoriteButton.setImageResource(R.drawable.ic_heart_filled); // Change button icon to filled heart
            }

            // Apply changes to SharedPreferences
            editor.apply();

            // Notify the activity (if it’s FavoritesActivity) to update the favorites screen
            if (context instanceof FavoritesActivity) {
                // Cast the context to FavoritesActivity and call a method to update the UI in FavoritesActivity
                FavoritesActivity favoritesActivity = (FavoritesActivity) context;
                favoritesActivity.updateFavorites(); // This should be a method in FavoritesActivity to refresh the list
            }
        });
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    // ViewHolder class to represent each item in the RecyclerView
    public static class GifViewHolder extends RecyclerView.ViewHolder {
        ImageView gifImage;
        ImageView favoriteButton;

        public GifViewHolder(View itemView) {
            super(itemView);
            gifImage = itemView.findViewById(R.id.gifImage);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }

    // Method to set the GIFs data when performing a search or updating the list
    public void setGifs(List<GiphyResponse.Data> gifs) {
        this.gifs = gifs;
        notifyDataSetChanged();
    }
}
