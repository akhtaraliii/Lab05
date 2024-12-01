package com.example.webapp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private GifAdapter adapter;
    private SharedPreferences sharedPreferences;
    private Set<String> favorites; // Set to hold the favorite GIF URLs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize the SharedPreferences object
        sharedPreferences = getSharedPreferences("FavoritesPrefs", MODE_PRIVATE);
        favorites = sharedPreferences.getStringSet("favorite_gifs", new HashSet<>());

        // Set up the RecyclerView and its adapter
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load the favorite GIFs from SharedPreferences
        List<GiphyResponse.Data> favoriteGifs = getFavoritesFromSharedPreferences();
        adapter = new GifAdapter(this, favoriteGifs);
        favoritesRecyclerView.setAdapter(adapter);
    }

    // Method to refresh the list of favorite GIFs
    public void updateFavorites() {
        List<GiphyResponse.Data> updatedFavorites = getFavoritesFromSharedPreferences();
        adapter.setGifs(updatedFavorites); // Update the adapter with the new list of favorite GIFs
        Toast.makeText(this, "Favorites updated", Toast.LENGTH_SHORT).show(); // Optional: Show a toast message
    }

    // Helper method to fetch the favorite GIFs from SharedPreferences
    private List<GiphyResponse.Data> getFavoritesFromSharedPreferences() {
        List<GiphyResponse.Data> favoriteGifs = new ArrayList<>();

        // Loop through the set of favorite GIF URLs and create Data objects (you may need to implement this depending on how you store the data)
        for (String url : favorites) {
            GiphyResponse.Data data = new GiphyResponse.Data();
            GiphyResponse.Data.Images images = new GiphyResponse.Data.Images();
            GiphyResponse.Data.Images.Original original = new GiphyResponse.Data.Images.Original();
            original.setUrl(url);
            images.setOriginal(original);
            data.setImages(images);
            favoriteGifs.add(data);
        }

        return favoriteGifs;
    }
}
