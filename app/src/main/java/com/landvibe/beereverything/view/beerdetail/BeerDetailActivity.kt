package com.landvibe.beereverything.view.beerdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.databinding.ActivityBeerdetailBinding

class BeerDetailActivity : AppCompatActivity() {
    private lateinit var beerViewModel: BeerDetailViewModel
    private var beerId = BEER_DEFAULT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBeerdetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_beerdetail)
        beerViewModel = ViewModelProvider(this).get(BeerDetailViewModel::class.java)
        binding.viewmodel = beerViewModel

        observeLiveData()
        loadBeer()
    }


    private fun loadBeer() {
        beerId = intent.getIntExtra(BEER_ID_KEY, BEER_DEFAULT_ID)
        if (beerId == BEER_DEFAULT_ID) {
            Toast.makeText(this, "not found", Toast.LENGTH_LONG).show()
            finish()
        }

        beerViewModel.loadBeer(beerId)
    }

    private fun observeLiveData() {
        beerViewModel.closeButtonClick.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }

    companion object {
        const val BEER_ID_KEY = "beer_id"
        private const val BEER_DEFAULT_ID = 0
    }
}
