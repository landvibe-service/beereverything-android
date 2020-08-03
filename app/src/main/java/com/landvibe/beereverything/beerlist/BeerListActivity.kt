package com.landvibe.beereverything.beerlist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.landvibe.beereverything.R
import com.landvibe.beereverything.data.BeerList
import kotlinx.android.synthetic.main.activity_beerlist.*

//DataSource.Factory & Livedata sample
class BeerListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: BeerListViewModel
    private lateinit var adapter : BeerListAdapter

    private val SORTBYNAME = 1
    private val SORTBYID = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beerlist)

        val recyclerView : RecyclerView = findViewById(R.id.beerlist_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter =  BeerListAdapter(this)

        viewModel= ViewModelProvider(this).get(BeerListViewModel::class.java)

        adapter.setItemClickListener(object : BeerListAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
            }
        })

        recyclerView.adapter = adapter
        subscribeUi(adapter)
        initButtonListeners()
    }

    private fun subscribeUi(adapter: BeerListAdapter){
        viewModel.getBeerListLiveData().observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun initButtonListeners(){
        id_sort_btn.setOnClickListener(this)
        name_sort_btn.setOnClickListener(this)
    }

    private fun sortBy(list: PagedList<BeerList>, state: Int){
        if(state == SORTBYID){
            viewModel.sortById()
            viewModel.getBeerListLiveData().observe(this, Observer {
                it?.let {
                    //adapter.onCurrentListChanged(list, it)
                    adapter.submitList(it)
                }
            })
        }
        else if(state == SORTBYNAME){
            viewModel.sortByName()
            viewModel.getBeerListLiveData().observe(this, Observer {
                it?.let {
                    //adapter.onCurrentListChanged( list, it)
                    adapter.submitList(it)
                }
            })
        }
    }


    override fun onClick(v: View) {
        val i = v.id
        when(i){
            R.id.id_sort_btn -> sortBy(
                viewModel.getBeerListLiveData().value!!, SORTBYID)
            R.id.name_sort_btn -> sortBy(
                viewModel.getBeerListLiveData().value!!, SORTBYNAME)
        }
    }

    companion object {
        private const val TAG = "BeerListActivity"
    }
}