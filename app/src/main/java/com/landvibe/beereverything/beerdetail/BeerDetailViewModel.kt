package com.landvibe.beereverything.beerdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.common.BEER_DATA
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.*


class BeerDetailViewModel : ViewModel() {
    private val beerListDao = AppDatabase.instance.beerDao()

    //접근 제한 때문에 _beer에만 값을 세팅해 준다 private에서, observe는 beer로
    private val _beer: MutableLiveData<Beer> = MutableLiveData()
    val beer: LiveData<Beer> = _beer

    private val _closeButtonClick: MutableLiveData<Boolean> = MutableLiveData(false)
    val closeButtonClick: LiveData<Boolean> = _closeButtonClick

    fun loadBeer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            //postvalue랑 아래 setvalue 차이는 구글링 한번 해보도록, io 쓰레드에서 메인으로 갈땐 post해줘야 함
            _beer.postValue(beerListDao.get(id))
        }
    }

    fun close() {
        _closeButtonClick.value = true
    }
}