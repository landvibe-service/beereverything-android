package com.landvibe.beereverything.common

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.data.BeerListDao

//DataSource.Factory & LiveData Sample
@Database(entities = [Beer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerListDao() : BeerListDao

    fun insertInit(){
        Log.d(TAG, "insertInit()")
        beerListDao().insert(
            BEER_DATA.map {
                Beer(
                    id = BEER_DATA.indexOf(it),
                    name = it
                )
            }
        )
    }

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

val BEER_DATA = arrayListOf(
    "hi", "beerdot", "hello",
    "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
    "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
    "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
    "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
    "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc"
)