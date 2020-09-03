package com.landvibe.beereverything.view.beerlist

import android.app.Application
import android.util.Log
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
    var searchText : MutableLiveData<String> = MutableLiveData()

    var beerList : LiveData<PagedList<Beer>> = Transformations.switchMap(_beerList){
        getBeerListLiveData()
    }
    init {
        val pagedListBuilder : LivePagedListBuilder<Int, Beer> = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10)
        beerList = pagedListBuilder.build()
    }

    private var _searchText = Observer<String> {searchBeer(it)}
    init {
        searchText.observeForever(_searchText)
    }

    fun sortByName(){
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

    fun searchBeer(input : String){
        Log.d(TAG, "input: $input")
        val list = LivePagedListBuilder<Int, Beer>(
            beerListDao.searchBeer("%"+ input + "%"), 10
        ).build()
        beerList = list
    }

    fun searchCancel(){
        val list =  LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10
        ).build()
        beerList = list
    }

    fun getBeerListLiveData() = beerList

    fun insertBeerList(beerList: List<Beer>) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.instance.beerDao().insertBeerList(beerList)
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