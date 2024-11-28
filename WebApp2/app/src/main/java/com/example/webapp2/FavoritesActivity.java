package com.example.webapp2;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private ListView favoritesListView;
    private FavoriteGifDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        favoritesListView = findViewById(R.id.favoritesListView);

        // Initialize the database
        database = FavoriteGifDatabase.getInstance(this);

        // Load and display favorite GIFs
        new Thread(() -> {
            List<FavoriteGif> favorites = database.favoriteGifDao().getAllFavorites();
            runOnUiThread(() -> {
                FavoritesAdapter adapter = new FavoritesAdapter(this, favorites);
                favoritesListView.setAdapter(adapter);
            });
        }).start();
    }
}
