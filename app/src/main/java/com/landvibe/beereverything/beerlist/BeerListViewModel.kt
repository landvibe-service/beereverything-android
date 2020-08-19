package com.landvibe.beereverything.beerlist

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.landvibe.beereverything.beerdetail.BeerDetailActivity
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//DataSource.Factory & LiveData Sample
class BeerListViewModel(app : Application) : AndroidViewModel(app){
    private val beerListDao = AppDatabase.instance.beerDao()
    private var beerList : LiveData<PagedList<Beer>>
    init{
        val pagedListBuilder : LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10
        )
        beerList = pagedListBuilder.build()

    }
    /*
      fun sortById(){
          val pagedListBuilder : LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
              beerListDao.allBeerListById(),
              10
          )
          beerList = pagedListBuilder.build()
          beerList.value?.dataSource?.invalidate()
      }

      fun sortByName(){
          Log.d(TAG, "sortByName()")
          val pagedListBuilder : LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
              beerListDao.allBeerListByName(),
              10
          )
          beerList = pagedListBuilder.build()
          beerList.value?.dataSource?.invalidate()

      }
      */

    fun sortByName() : LiveData<PagedList<Beer>>{
        val source = beerListDao.allBeerListByName()
        return LivePagedListBuilder(source, 10).build()
    }

    fun sortById() : LiveData<PagedList<Beer>>{
        val source = beerListDao.allBeerListById()
        return LivePagedListBuilder(source, 10).build()
    }

    fun getBeerListLiveData() = beerList

    fun loadInit(){
        viewModelScope.launch(Dispatchers.IO){
            AppDatabase.instance.insertInit()
        }
    }

    fun clearBeerList(){
        viewModelScope.launch(Dispatchers.IO){
            AppDatabase.instance.beerDao().deleteAll()
        }
    }

    companion object {
        private const val TAG = "BeerListViewModel"
    }
}