package com.example.webapp2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteGif.class}, version = 1, exportSchema = false)
public abstract class FavoriteGifDatabase extends RoomDatabase {

    private static volatile FavoriteGifDatabase INSTANCE;

    public abstract FavoriteGifDao favoriteGifDao();

    public static FavoriteGifDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteGifDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FavoriteGifDatabase.class, "favorite_gif_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
