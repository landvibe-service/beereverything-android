package com.landvibe.beereverything.view.beerlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.databinding.ActivityBeerlistBinding
import com.landvibe.beereverything.view.beerdetail.BeerDetailActivity

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

    private fun observeLiveData(){
        viewModel.beerList.observe(this, Observer {
            it?.let {
                beerListAdapter.submitList(it)
            }
        })
    }

    fun sortByName(){
        viewModel.sortByName()
        observeLiveData()
    }

    fun sortById(){
        viewModel.sortById()
        observeLiveData()
    }

    override fun onDestroy() {
        viewModel.clearBeerList()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "BeerListActivity"
    }
}