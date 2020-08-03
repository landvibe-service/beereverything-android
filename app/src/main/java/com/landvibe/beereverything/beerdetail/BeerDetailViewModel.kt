package com.landvibe.beereverything.beerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.landvibe.beereverything.data.BeerList
import com.landvibe.beereverything.data.BeerListDB
import kotlinx.coroutines.*


class BeerDetailViewModel : ViewModel() {
    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val beerListDao = BeerListDB.instance!!.beerListDao()

    fun getBeerDetail(id : Int): LiveData<BeerList> {
        return beerListDao.get(id)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}