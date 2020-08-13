package com.landvibe.beereverything.beerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.*


class BeerDetailViewModel : ViewModel() {
    private val beerListDao = AppDatabase.instance.beerDao()

    private val _beer: MutableLiveData<Beer> = MutableLiveData()
    val beer: LiveData<Beer> = _beer

    private val _closeButtonClick: MutableLiveData<Boolean> = MutableLiveData(false)
    val closeButtonClick: LiveData<Boolean> = _closeButtonClick

    fun loadBeer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _beer.postValue(beerListDao.get(id))
        }
    }

    fun close() {
        _closeButtonClick.value = true
    }
}