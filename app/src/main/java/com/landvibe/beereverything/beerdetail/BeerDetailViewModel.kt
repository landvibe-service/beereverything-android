package com.landvibe.beereverything.beerdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.common.BEER_DATA
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.*


class BeerDetailViewModel : ViewModel() {
    private val job = Job()
    private val beerListDao = AppDatabase.instance.beerListDao()
    lateinit var beer : LiveData<Beer>

    fun loadBeerDetail(id : Int) : LiveData<Beer> {

        viewModelScope.launch(Dispatchers.Main){
            beer = beerListDao.get(id)
        }
        return beer

        /*
        val beer = beerListDao.get(id).let {
            beerListDao.get(id)
        }
        return beer
         */
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}