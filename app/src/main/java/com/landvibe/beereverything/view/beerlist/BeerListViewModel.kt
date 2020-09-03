package com.landvibe.beereverything.view.beerlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerListViewModel(app: Application) : AndroidViewModel(app) {
    private val beerListDao = AppDatabase.instance.beerDao()

    var beerList: LiveData<PagedList<Beer>> = LivePagedListBuilder<Int, Beer>(
        beerListDao.allBeerListById(),
        10
    ).build()

    private var _searchText: MutableLiveData<String> = MutableLiveData()
    var searchText: LiveData<String> = _searchText

    private val _sideMenuOpen: MutableLiveData<Boolean> = MutableLiveData(false)
    val sideMenuOpen: LiveData<Boolean> = _sideMenuOpen

    private val _isSearchMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSearchMode: LiveData<Boolean> = _isSearchMode

    fun setSideMenu(visible: Boolean) {
        _sideMenuOpen.value = visible
    }

    fun setSearchMode(enable: Boolean) {
        _isSearchMode.value = enable
    }

    fun sortByName() {
        beerList = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListByName(),
            10
        ).build()
    }

    fun sortById() {
        beerList = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10
        ).build()

    }

    fun searchBeer(input: String) {
        _searchText.value = input
        beerList = LivePagedListBuilder<Int, Beer>(
            beerListDao.searchBeer("%$input%"), 10
        ).build()
    }

    fun searchCancel() {
        beerList = LivePagedListBuilder<Int, Beer>(
            beerListDao.allBeerListById(),
            10
        ).build()
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
}