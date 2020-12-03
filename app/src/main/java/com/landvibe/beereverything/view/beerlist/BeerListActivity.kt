package com.landvibe.beereverything.view.beerlist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.landvibe.beereverything.R
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ActivityBeerlistBinding
import com.landvibe.beereverything.view.beerdetail.BeerDetailActivity

class BeerListActivity : AppCompatActivity() {
    private lateinit var viewModel: BeerListViewModel
    private lateinit var beerListAdapter: BeerListAdapter
    private lateinit var binding: ActivityBeerlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beerlist)
        viewModel = ViewModelProvider(this).get(BeerListViewModel::class.java)
        beerListAdapter = BeerListAdapter(onItemClickListener())
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.beerlistRecyclerView.adapter = beerListAdapter
        binding.drawerLayout.beerPick.setOnClickListener {
            Toast.makeText(this, "맥주 추천", Toast.LENGTH_LONG).show()
        }
        binding.drawerLayout.beerToday.setOnClickListener {
            Toast.makeText(this, "오늘의 맥주", Toast.LENGTH_LONG).show()
        }
        observeLiveData()
        addEditTextEvent()
        viewModel.syncBeerListIfNeed()
    }

    private fun onItemClickListener(): BeerListAdapter.OnItemClickListener {
        return object : BeerListAdapter.OnItemClickListener {
            override fun onItemClick(beer: Beer) {
                startActivity(
                    Intent(
                        this@BeerListActivity,
                        BeerDetailActivity::class.java
                    ).putExtra(BeerDetailActivity.EXTRA_BEER_DATA, beer)
                )
            }
        }
    }

    private fun addEditTextEvent() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.search(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun observeLiveData() {
        viewModel.beerList.observe(this, Observer {
            beerListAdapter.submitList(it)
        })

        viewModel.isSearchMode.observe(this, Observer {
            if (it) {
                binding.searchInput.setText("")
                //hidekeyboard
            }
        })
    }
}