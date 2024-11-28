package com.example.webapp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton, showFavoritesButton;
    private RecyclerView recyclerView;
    private GifAdapter adapter;
    private static final String API_KEY = "zsPKMbDTcz2BQJrIBoGtJV77ANq9qWgS"; // Replace with your actual API Key
    private FavoriteGifDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        showFavoritesButton = findViewById(R.id.showFavoritesButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Set up RecyclerView and Adapter
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize the database
        database = FavoriteGifDatabase.getInstance(this);

        // Initialize the adapter with a click listener for the favorite button
        adapter = new GifAdapter(this, gif -> {
            if (gif != null && gif.getUrl() != null) {
                // Add the favorite gif to the database
                FavoriteGif favoriteGif = new FavoriteGif(gif.getUrl());
                new Thread(() -> {
                    database.favoriteGifDao().insertFavorite(favoriteGif);
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Added to Favorites!", Toast.LENGTH_SHORT).show());
                }).start();
            }
        });

        recyclerView.setAdapter(adapter);

        // Search Button functionality
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                searchGIFs(query);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            }
        });

        // Show Favorites Button functionality
        showFavoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    private void searchGIFs(String query) {
        // Create instance of API service
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Call the API to search GIFs
        apiService.searchGIFs(API_KEY, query, 40).enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GiphyResponse.Data> gifs = response.body().getData();
                    adapter.setGifs(gifs);
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching GIFs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GiphyResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch GIFs", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
