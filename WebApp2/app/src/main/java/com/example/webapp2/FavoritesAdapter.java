package com.example.webapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoritesAdapter extends ArrayAdapter<FavoriteGif> {

    public FavoritesAdapter(Context context, List<FavoriteGif> favorites) {
        super(context, 0, favorites);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_gif, parent, false);
        }

        FavoriteGif favoriteGif = getItem(position);

        // Use Glide to load the GIF into the ImageView
        ImageView gifImageView = convertView.findViewById(R.id.gifImageView);
        Glide.with(getContext())
                .load(favoriteGif.getGifUrl())  // Assuming the URL is stored in the `FavoriteGif` object
                .into(gifImageView);

        return convertView;
    }
}
