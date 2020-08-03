package com.landvibe.beereverything

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.adapter.BeerAdapter
import com.landvibe.beereverything.databinding.ActivityBeerListBinding
import com.landvibe.beereverything.viewmodel.BeerViewModel
import com.landvibe.beereverything.viewmodel.BeerViewModelFactory

private lateinit var binding: ActivityBeerListBinding

class BeerListActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer_list)

        binding.viewmodel = ViewModelProvider(this, BeerViewModelFactory(application))
            .get(BeerViewModel::class.java)

        initializeList()
    }


    private fun initializeList() {
        val beerAdapter = BeerAdapter()

        binding.rvBeerList.adapter = beerAdapter

        binding.viewmodel!!.beerList.observe(this, Observer(beerAdapter::submitList))
    }
}