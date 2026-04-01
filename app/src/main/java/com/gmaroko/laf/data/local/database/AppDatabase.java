package com.gmaroko.laf.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gmaroko.laf.data.local.dao.ItemDao;
import com.gmaroko.laf.data.local.entity.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "lost_and_found_db"
                    ).fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}