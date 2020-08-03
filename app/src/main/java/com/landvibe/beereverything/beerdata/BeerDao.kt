package com.landvibe.beereverything.beerdata

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface BeerDao {

    @Query("SELECT * FROM Beer")
    fun getAll(): List<Beer>

    @Query("SELECT * FROM Beer ORDER BY title ASC")
    fun getBeerList(): DataSource.Factory<Int, Beer>

    @Insert
    fun insert(beer: Beer)

    @Update
    fun update(beer: Beer)

    @Delete
    fun delete(beer: Beer)

    @Query("DELETE FROM Beer")
    fun deleteAll()
}