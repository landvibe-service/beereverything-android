package com.landvibe.beereverything.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

//import androidx.paging.PagingSource

@Dao
interface BeerDao {
    @Query("SELECT * FROM beer")
    fun getAll(): LiveData<Beer>

    @Query("SELECT * FROM beer ORDER BY name COLLATE NOCASE ASC")
    fun allBeerListByName() : DataSource.Factory<Int, Beer>

    @Query("SELECT * FROM beer ORDER BY id COLLATE NOCASE ASC")
    fun allBeerListById() : DataSource.Factory<Int, Beer>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertBeerList(beerList : List<Beer>)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertBeer(beer : Beer)

    @Delete
    fun deleteBeer(beer: Beer)

    @Query("DELETE from beer")
    fun deleteAll()

    @Query("SELECT * FROM beer WHERE id = :id")
    fun getBeer(id: Int): Beer
}

