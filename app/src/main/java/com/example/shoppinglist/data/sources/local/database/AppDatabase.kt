package com.example.shoppinglist.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppinglist.data.sources.local.entities.NoteEntity
import com.example.shoppinglist.data.sources.local.entities.ShopListItemEntity

@Database(entities = [NoteEntity::class, ShopListItemEntity::class], version = 1)
@TypeConverters(LocalDateTimeTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun shopListDao(): ShopListDao

    companion object{
        const val DATABASE_NAME: String = "crime_database"
    }
}