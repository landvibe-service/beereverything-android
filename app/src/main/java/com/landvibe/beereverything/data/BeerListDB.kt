package com.landvibe.beereverything.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

//DataSource.Factory & LiveData Sample
//singleton database object
@Database(entities = [BeerList::class], version = 1)
abstract class BeerListDB : RoomDatabase() {
    abstract fun beerListDao() : BeerListDao

    companion object {
        @Volatile
        var instance: BeerListDB? = null

        fun get(context: Context): BeerListDB {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BeerListDB {
            return Room.databaseBuilder(context, BeerListDB::class.java, "BeerListDatabase")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            get(context).beerListDao().insert(
                                BEER_DATA.map { BeerList(id = 0, name = it, price = 0) })
                        }
                    }
                }).build()
        }
    }
}

private val BEER_DATA = arrayListOf(
    "hi", "beerdot", "hello",
    "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
    "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
    "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
    "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
    "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
    "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
    "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
    "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
    "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
    "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
    "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
    "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello"
)