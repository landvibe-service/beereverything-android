package com.landvibe.beereverything.beerlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.paging.PagedList.Config
import androidx.recyclerview.widget.DiffUtil
import com.landvibe.beereverything.data.BeerList
import com.landvibe.beereverything.data.BeerListDB

//DataSource.Factory & LiveData Sample
class BeerListViewModel(app : Application) : AndroidViewModel(app){
    private val beerListDao = BeerListDB.get(app).beerListDao()

    private var beerList : LiveData<PagedList<BeerList>>
    init{
        val pagedListBuilder : LivePagedListBuilder<Int, BeerList> = LivePagedListBuilder<Int, BeerList>(
            beerListDao.allBeerListByName(),
            20
        )
        beerList = pagedListBuilder.build()
    }

    fun sortById(){
        val pagedListBuilder : LivePagedListBuilder<Int, BeerList> = LivePagedListBuilder<Int, BeerList>(
            beerListDao.allBeerListById(),
            20
        )
        beerList = pagedListBuilder.build()
    }

    fun sortByName(){
        val pagedListBuilder : LivePagedListBuilder<Int, BeerList> = LivePagedListBuilder<Int, BeerList>(
            beerListDao.allBeerListByName(),
            20
        )
        beerList = pagedListBuilder.build()
    }

    fun getBeerListLiveData() = beerList

    companion object {
        private const val TAG = "BeerListViewModel"
    }
}