package com.landvibe.beereverything.view.beerdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.databinding.ActivityBeerdetailBinding

class BeerDetailActivity  : AppCompatActivity() {
    private lateinit var beerViewModel: BeerDetailViewModel
    private var beerId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityBeerdetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_beerdetail)
        beerViewModel = ViewModelProvider(this).get(BeerDetailViewModel::class.java)
        binding.viewmodel = beerViewModel

        observeLiveData()
        loadBeer()
    }


    fun loadBeer(){
        beerId = intent.getIntExtra("beer_id", 0)
        if (beerId == 0) {
            Toast.makeText(this, "not found", Toast.LENGTH_LONG).show()
            finish()
        }

        beerViewModel.loadBeer(beerId)
    }

    private fun observeLiveData() {
        beerViewModel.beer.observe(this, Observer {
            Toast.makeText(this, "beer loaded", Toast.LENGTH_LONG).show()
        })

        beerViewModel.closeButtonClick.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }

    companion object {
        private const val TAG = "BeerDetailActivity"
    }
}
