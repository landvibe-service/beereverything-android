package com.landvibe.beereverything.view.beerlist

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerListViewModel(app: Application) : AndroidViewModel(app) {
    private val beerListDao = AppDatabase.instance.beerDao()

    private val _sortType = MutableLiveData<SortType>(SortType.ID)

    private var _searchText: MutableLiveData<String> = MutableLiveData()

    private val beerMeta = MediatorLiveData<Pair<String, SortType>>()

    private val _sideMenuOpen: MutableLiveData<Boolean> = MutableLiveData(false)
    val sideMenuOpen: LiveData<Boolean> = _sideMenuOpen

    private val _isSearchMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSearchMode: LiveData<Boolean> = _isSearchMode

    val beerList = beerMeta.switchMap {
        when (it.second) {
            SortType.ID -> {
                LivePagedListBuilder<Int, Beer>(
                    beerListDao.allBeerListById("%${it.first}%"), 10
                ).build()
            }

            else -> {
                LivePagedListBuilder<Int, Beer>(
                    beerListDao.allBeerListByName("%${it.first}%"), 10
                ).build()
            }
        }
    }

    init {
        beerMeta.apply {
            addSource(_searchText) {
                beerMeta.value = Pair(it, _sortType.value ?: SortType.ID)
            }

            addSource(_sortType) {
                beerMeta.value = Pair(_searchText.value ?: "", it)
            }
        }
    }

    fun setSideMenu(visible: Boolean) {
        _sideMenuOpen.value = visible
    }

    fun setSearchMode(enable: Boolean) {
        _isSearchMode.value = enable
        _searchText.value = ""
    }

    fun sort(type: SortType) {
        _sortType.value = type
    }

    fun search(input: String) {
        _searchText.value = input
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

    enum class SortType {
        NAME, ID
    }
}

