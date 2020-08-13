package com.landvibe.beereverything.beerdetail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ActivityBeerdetailBinding
import kotlinx.android.synthetic.main.activity_beerdetail.*

class BeerDetailActivity  : AppCompatActivity() {
    private lateinit var viewModel : BeerDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityBeerdetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_beerdetail)
        viewModel = ViewModelProvider(this).get(BeerDetailViewModel::class.java)
        var beerId = -1

        if(intent.getIntExtra("beer_id", 0) != 0){
            beerId = intent.getIntExtra("beer_id", 0)
            Log.d(TAG, "beer_id: $beerId")
        }

        val beer = viewModel.loadBeerDetail(beerId)
        Log.d(TAG, "${beer.value!!.name}")
        Log.d(TAG, "${beer.value!!.id}")
        Log.d(TAG, "${beer.toString()}")
        beer.observe(this,
            Observer {
                binding.beerItem = it

                Log.d(TAG, "binding - name: ${binding.beerItem?.name}")
                Log.d(TAG, "binding - id: ${binding.beerItem?.id}")
                Log.d(TAG, "it - name: ${it.name}")
                Log.d(TAG, "it - id: ${it.id}")
            }
        )

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        attachButtonEvents()
        binding.activity = this
    }


    fun attachButtonEvents(){
        finish()
    }

    companion object {
        private const val TAG = "BeerDetailActivity"
    }
}