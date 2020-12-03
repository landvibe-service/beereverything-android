package com.landvibe.beereverything.view.beerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerDetailViewModel : ViewModel() {

    private val _closeButtonClick: MutableLiveData<Boolean> = MutableLiveData(false)
    val closeButtonClick: LiveData<Boolean> = _closeButtonClick

    fun close() {
        _closeButtonClick.value = true
    }
}