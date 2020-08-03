package com.landvibe.beereverything

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBeerListActivityButton = findViewById<Button>(R.id.btn_start_beer_list_ac)
        startBeerListActivityButton.setOnClickListener {
            val intent = Intent(this, BeerListActivity::class.java)
            startActivity(intent)
        }
    }
}