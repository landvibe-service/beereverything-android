package com.landvibe.beereverything.view.beerlist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.databinding.ActivityBeerlistBinding
import com.landvibe.beereverything.view.beerdetail.BeerDetailActivity

class BeerListActivity : AppCompatActivity() {
    private lateinit var viewModel: BeerListViewModel
    private lateinit var beerListAdapter: BeerListAdapter
    lateinit var binding: ActivityBeerlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beerlist)
        viewModel = ViewModelProvider(this).get(BeerListViewModel::class.java)
        beerListAdapter = BeerListAdapter(this)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.beerlistRecyclerView.adapter = beerListAdapter
        observeLiveData()
        setupRecyclerView()
        addEditTextEvent()
    }

    private fun addEditTextEvent() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                viewModel.searchBeer(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun setupRecyclerView() {
        beerListAdapter.setOnItemClickListener(object : BeerListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, id: Int) {
                startActivity(
                    Intent(
                        this@BeerListActivity,
                        BeerDetailActivity::class.java
                    ).putExtra(BeerDetailActivity.BEER_ID_KEY, id)
                )
            }
        })
    }

    private fun observeLiveData() {
        viewModel.searchText.observe(this, Observer {
            it
        })

        viewModel.beerList.observe(this, Observer {
            beerListAdapter.submitList(it)
        })
    }
}