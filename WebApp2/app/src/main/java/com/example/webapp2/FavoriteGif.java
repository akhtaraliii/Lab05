package com.example.webapp2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_gifs")
public class FavoriteGif {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String gifUrl; // URL or path for the GIF

    public FavoriteGif(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    // Getters and Setters
    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
