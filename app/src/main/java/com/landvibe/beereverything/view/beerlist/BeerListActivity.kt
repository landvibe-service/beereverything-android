package com.landvibe.beereverything.view.beerlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.databinding.ActivityBeerlistBinding
import com.landvibe.beereverything.view.beerdetail.BeerDetailActivity
import kotlinx.android.synthetic.main.activity_beerlist.*

//DataSource.Factory & Livedata sample
class BeerListActivity : AppCompatActivity() {
    private lateinit var viewModel: BeerListViewModel
    private lateinit var beerListAdapter: BeerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityBeerlistBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_beerlist)
        viewModel = ViewModelProvider(this).get(BeerListViewModel::class.java)
        beerListAdapter = BeerListAdapter(this)

        binding.viewmodel = viewModel
        binding.activity = this
        binding.adapter = beerListAdapter
        binding.beerlistRecyclerView.adapter = beerListAdapter

        viewModel.getBeerListLiveData().observe(this, Observer {
            it?.let {
                beerListAdapter.submitList(it)
            }
        })

        beerListAdapter.setOnItemClickListener(object : BeerListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, id: Int) {
                val intent = Intent(this@BeerListActivity, BeerDetailActivity::class.java)
                intent.putExtra("beer_id", id)
                startActivity(intent)
            }
        })

        viewModel.searchText.observe(this, object : Observer<String> {
            override fun onChanged(t: String) {
                observeLiveData()
            }
        })
    }

    private fun observeLiveData() {
        Log.d(TAG, "observeLiveData()")

        viewModel.beerList.observe(this, Observer {
            it?.let {
                beerListAdapter.submitList(it)
            }
        })
    }

    fun clickSearchButton() {
        upper_menu_layout.visibility = View.GONE
        search_button_layout.visibility = View.VISIBLE
    }

    fun clickSearchCancelButton() {
        upper_menu_layout.visibility = View.VISIBLE
        search_button_layout.visibility = View.GONE
        viewModel.searchCancel()
    }

    fun clickHamburgerButton() {
        drawer_layout.visibility = View.VISIBLE
        drawer_background_layout.visibility = View.VISIBLE
    }

    fun closeDrawerLayout(){
        drawer_layout.visibility = View.GONE
        drawer_background_layout.visibility = View.GONE
    }

    fun sortByName() {
        viewModel.sortByName()
        observeLiveData()
    }

    fun sortById() {
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