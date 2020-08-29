package com.landvibe.beereverything.beerlist

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//DataSource.Factory & LiveData Sample
class BeerListViewModel(app : Application) : AndroidViewModel(app){
    private val beerListDao = AppDatabase.instance.beerDao()

    private var _beerList : MutableLiveData<LiveData<PagedList<Beer>>> = MutableLiveData()
    var beerList : LiveData<PagedList<Beer>> = Transformations.switchMap(_beerList){
        getBeerListLiveData()
    }
    init {
        val pagedListBuilder : LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10)
        beerList = pagedListBuilder.build()
    }

    fun sortByName(){
        //_beerlist의 값을 update
        val list = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListByName(),
            10
        ).build()
        beerList = list
    }

    fun sortById(){
        val list =  LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10
        ).build()
        beerList = list
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