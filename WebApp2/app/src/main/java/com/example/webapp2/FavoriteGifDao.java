package com.example.webapp2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteGifDao {
    @Insert
    void insertFavorite(FavoriteGif favoriteGif);

    @Query("SELECT * FROM favorite_gifs")
    List<FavoriteGif> getAllFavorites();
}
