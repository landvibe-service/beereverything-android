package com.landvibe.beereverything.data

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer WHERE name LIKE :query ORDER BY name COLLATE NOCASE ASC")
    fun allBeerListByName(query: String): DataSource.Factory<Int, Beer>

    @Query("SELECT * FROM beer WHERE name LIKE :query ORDER BY id COLLATE NOCASE ASC")
    fun allBeerListById(query: String): DataSource.Factory<Int, Beer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeerList(beerList: List<Beer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBeer(beer: Beer)

    @Delete
    fun deleteBeer(beer: Beer)

    @Query("DELETE from beer")
    fun deleteAll()

    @Query("SELECT * FROM beer WHERE id = :id")
    fun getBeer(id: Int): Beer
}

