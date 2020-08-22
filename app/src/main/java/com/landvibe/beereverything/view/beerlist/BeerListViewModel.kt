package com.landvibe.beereverything.view.beerlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//DataSource.Factory & LiveData Sample
class BeerListViewModel(app: Application) : AndroidViewModel(app) {
    private val beerListDao = AppDatabase.instance.beerDao()
    private var beerList: LiveData<PagedList<Beer>>

    init {
        val pagedListBuilder: LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10
        )
        beerList = pagedListBuilder.build()
    }

    fun sortById() {
        val pagedListBuilder: LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10
        )
        beerList = pagedListBuilder.build()
    }

    fun sortByName() {
        val pagedListBuilder: LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListByName(),
            10
        )
        beerList = pagedListBuilder.build()
    }

    fun getBeerListLiveData() = beerList

    fun loadInit() {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.instance.insertInit()
        }
    }

    fun insertBeer(beer: Beer) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.instance.beerDao().insertBeer(beer)
        }
    }

    fun insertBeerList(beerList: List<Beer>) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.instance.beerDao().insertBeerList(beerList)
        }
    }

    fun clearBeerList() {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.instance.beerDao().deleteAll()
        }
    }

    companion object {
        private const val TAG = "BeerListViewModel"
    }
}