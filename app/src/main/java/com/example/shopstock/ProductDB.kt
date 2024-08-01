package com.example.shopstock

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDao
    companion object {
        @Volatile
        private var INSTANCE: ProductDB? = null

        fun getDatabase(context: Context): ProductDB{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDB::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
