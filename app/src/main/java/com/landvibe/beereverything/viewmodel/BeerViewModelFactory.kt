package com.landvibe.beereverything.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BeerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BeerViewModel::class.java)) {
            BeerViewModel(application) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}