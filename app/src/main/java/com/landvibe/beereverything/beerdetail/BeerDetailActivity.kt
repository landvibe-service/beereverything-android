package com.landvibe.beereverything.beerdetail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import kotlinx.android.synthetic.main.activity_beerdetail.*

class BeerDetailActivity  : AppCompatActivity() {
    private lateinit var viewModel : BeerDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beerdetail)

        var beerId = -1

        if(intent.getIntExtra("beer_id", 0)!= 0){
            beerId = intent.getIntExtra("beer_id", 0)
            Log.d(TAG, "beer_id: $beerId")
        }

        viewModel = ViewModelProvider(this).get(BeerDetailViewModel::class.java)
        loadView(beerId)
        attachButtonEvents()
    }

    private fun attachButtonEvents(){
        back_main_btn.setOnClickListener{
            finish()
        }
    }

    private fun loadView(id : Int){
        viewModel.getBeerDetail(id).observe(this,
        Observer {
            beerdetail_name.text = it.name
            beerdetail_desc.text = it.id.toString()
        })
    }

    companion object {
        private const val TAG = "BeerDetailActivity"
    }
}