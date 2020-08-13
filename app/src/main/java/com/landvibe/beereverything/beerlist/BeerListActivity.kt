package com.landvibe.beereverything.beerlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.landvibe.beereverything.R
import com.landvibe.beereverything.beerdetail.BeerDetailActivity
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ActivityBeerlistBinding
import kotlinx.android.synthetic.main.activity_beerlist.*

//DataSource.Factory & Livedata sample
class BeerListActivity : AppCompatActivity() {
    private lateinit var viewModel: BeerListViewModel
    private lateinit var beerListAdapter : BeerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityBeerlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_beerlist)
        viewModel = ViewModelProvider(this).get(BeerListViewModel::class.java)
        beerListAdapter =  BeerListAdapter(this)

        binding.viewmodel = viewModel
        binding.activity = this
        binding.adapter = beerListAdapter
        binding.beerlistRecyclerView.adapter = beerListAdapter

        viewModel.loadInit()
        viewModel.getBeerListLiveData().observe(this, Observer {
            it?.let {
                beerListAdapter.submitList(it)
            }
        })

        beerListAdapter.setOnItemClickListener(object : BeerListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, id: Int) {
                val intent = Intent(this@BeerListActivity, BeerDetailActivity::class.java)
                intent.putExtra("beer_id", id)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        viewModel.clearBeerList()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "BeerListActivity"
    }
}