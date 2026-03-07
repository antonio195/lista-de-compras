package com.antoniocostadossantos.listadecompras.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antoniocostadossantos.listadecompras.domain.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}