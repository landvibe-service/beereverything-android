package com.landvibe.beereverything.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.common.Constants
import com.landvibe.beereverything.common.Feature
import com.landvibe.beereverything.common.RefreshDbActivity
import com.landvibe.beereverything.view.beerlist.BeerListActivity
import com.landvibe.beereverything.view.beerlist.BeerListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshLocalDB()

        btn_start_beer_list_ac.setOnClickListener(this)
        if (Feature.isFeatureEnabled(Constants.FEATURE_DB_REFRESH)) {
            btn_start_refresh_db_ac.visibility = VISIBLE;
            btn_start_refresh_db_ac.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start_beer_list_ac -> startBeerListActivity()
            R.id.btn_start_refresh_db_ac -> startRefreshDbActivity()
        }
    }

    private fun refreshLocalDB() {
        val beerListViewModel = ViewModelProvider(this).get(BeerListViewModel::class.java)

        val refreshDbActivity = RefreshDbActivity()
        refreshDbActivity.clearLocalDB(beerListViewModel)
        refreshDbActivity.loadFireStore(beerListViewModel)
    }

    private fun startBeerListActivity() {
        val intent = Intent(this, BeerListActivity::class.java)
        startActivity(intent)
    }

    private fun startRefreshDbActivity() {
        val intent = Intent(this, RefreshDbActivity::class.java)
        startActivity(intent)
    }
}
