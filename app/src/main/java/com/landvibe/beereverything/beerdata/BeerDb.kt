package com.landvibe.beereverything.beerdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Beer::class], version = 1)
abstract class BeerDb : RoomDatabase() {
    abstract fun beerDao(): BeerDao

    companion object {
        private const val DB_NAME = "beer_database.db"
        private var instance: BeerDb? = null

        @Synchronized
        fun getInstance(context: Context) : BeerDb {
            if(instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    BeerDb::class.java, DB_NAME)
                    .build()
            }
            return instance!!
        }
    }

}