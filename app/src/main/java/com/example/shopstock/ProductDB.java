package com.example.shopstock;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Product.class}, version = 3) // Update version number
public abstract class ProductDB extends RoomDatabase {
    public abstract ProductDao productDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Define your migration strategy if needed
            // database.execSQL("ALTER TABLE product ADD COLUMN new_column TEXT");
        }
    };
}
