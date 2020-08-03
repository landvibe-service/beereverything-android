package com.landvibe.beereverything.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
//import androidx.paging.PagingSource


@Dao
interface BeerListDao {
    @Query("SELECT * FROM beerlist")
    fun getAll(): List<BeerList>

    @Query("SELECT * FROM beerlist ORDER BY name COLLATE NOCASE ASC")
    fun allBeerListByName() : DataSource.Factory<Int, BeerList>

    @Query("SELECT * FROM beerlist ORDER BY id COLLATE NOCASE ASC")
    fun allBeerListById() : DataSource.Factory<Int, BeerList>

    @Insert
    fun insert(beerList : List<BeerList>)

    @Insert
    fun insert(beerList : BeerList)

    @Delete
    fun delete(beerList: BeerList)

    @Query("SELECT * FROM beerlist WHERE id = :id")
    fun get(id: Int): LiveData<BeerList>

}