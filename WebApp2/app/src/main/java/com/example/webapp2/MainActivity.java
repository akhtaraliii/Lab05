package com.example.webapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton, favoritesButton;
    private RecyclerView recyclerView;
    private GifAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views by ID
        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        favoritesButton = findViewById(R.id.favoritesButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Set a GridLayoutManager with 2 columns (you can adjust the number of columns based on your preference)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));  // 2 columns for a grid

        // Initialize GifAdapter with context and an empty list of GIFs
        adapter = new GifAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Search button click listener
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString();
            if (!query.isEmpty()) {
                searchGIFs(query);
            }
        });

        // Favorites button click listener
        favoritesButton.setOnClickListener(v -> {
            // Start the FavoritesActivity to view saved favorites
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    // Method to search GIFs using the API
    private void searchGIFs(String query) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.searchGIFs("ZZ4zeqBJIszvky8cfjEX3DNuYA5J76bT", query, 20).enqueue(new retrofit2.Callback<GiphyResponse>() {
            @Override
            public void onResponse(retrofit2.Call<GiphyResponse> call, retrofit2.Response<GiphyResponse> response) {
                if (response.body() != null) {
                    List<GiphyResponse.Data> gifs = response.body().getData();
                    adapter.setGifs(gifs);  // Update the adapter with new GIFs
                }
            }

            @Override
            public void onFailure(retrofit2.Call<GiphyResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
