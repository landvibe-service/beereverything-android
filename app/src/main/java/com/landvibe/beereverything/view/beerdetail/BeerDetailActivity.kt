package com.landvibe.beereverything.view.beerdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ActivityBeerdetailBinding

class BeerDetailActivity : AppCompatActivity() {
    private lateinit var beerViewModel: BeerDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBeerdetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_beerdetail)
        beerViewModel = ViewModelProvider(this).get(BeerDetailViewModel::class.java)
        binding.beerItem = intent.getSerializableExtra(EXTRA_BEER_DATA) as Beer
        binding.viewmodel = beerViewModel

        observeLiveData()
    }

    private fun observeLiveData() {
        beerViewModel.closeButtonClick.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }

    companion object {
        const val EXTRA_BEER_DATA = "beer_data"
    }
}
