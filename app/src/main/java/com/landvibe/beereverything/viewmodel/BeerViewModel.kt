package com.landvibe.beereverything.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.landvibe.beereverything.beerdata.Beer
import com.landvibe.beereverything.beerdata.BeerDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerViewModel(application: Application) : ViewModel() {

    var beerList: LiveData<PagedList<Beer>>
    var newBeerTitle: String? = null
    private val beerDao = BeerDb.getInstance(application).beerDao()

    init {
        val factory: DataSource.Factory<Int, Beer> = beerDao.getBeerList()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .setPageSize(20).build();
        val beerListBuilder: LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(factory, config)
        beerList = beerListBuilder.build()
    }

    fun insertBeer(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            beerDao.insert(Beer(0, title))
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            beerDao.deleteAll()
        }
    }

}