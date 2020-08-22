package com.landvibe.beereverything.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.landvibe.beereverything.R
import com.landvibe.beereverything.common.RefreshDbActivity
import com.landvibe.beereverything.view.beerlist.BeerListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start_beer_list_ac.setOnClickListener(this)
        btn_start_refresh_db_ac.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start_beer_list_ac -> startBeerListActivity()
            R.id.btn_start_refresh_db_ac -> refreshLocalDB()
        }
    }

    private fun startBeerListActivity() {
        val intent = Intent(this, BeerListActivity::class.java)
        startActivity(intent)
    }

    private fun refreshLocalDB() {
        val intent = Intent(this, RefreshDbActivity::class.java)
        startActivity(intent)
    }
}
