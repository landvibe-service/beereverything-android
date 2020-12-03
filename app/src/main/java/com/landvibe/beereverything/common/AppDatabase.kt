package com.landvibe.beereverything.common

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.data.BeerDao

@Database(entities = [Beer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao

    companion object {
        private const val TAG = "AppDatabase"
        val instance = Room.databaseBuilder(
            App.instance,
            AppDatabase::class.java, "beereverything.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}