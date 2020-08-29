package com.landvibe.beereverything.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface BeerListDao {
    @Query("SELECT * FROM beer")
    fun getAll(): LiveData<Beer>

    @Query("SELECT * FROM beer ORDER BY name COLLATE NOCASE ASC")
    fun allBeerListByName() : DataSource.Factory<Int, Beer>
    //fun allBeerListByName() : PagedList<Beer>

    @Query("SELECT * FROM beer ORDER BY id COLLATE NOCASE ASC")
    fun allBeerListById() : DataSource.Factory<Int, Beer>
    //fun allBeerListById() : PagedList<Beer>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(beer : List<Beer>)

    @Delete
    fun delete(beer: Beer)

    @Query("DELETE from beer")
    fun deleteAll()

    @Query("SELECT * FROM beer WHERE id = :id")
    fun get(id: Int): Beer
}

