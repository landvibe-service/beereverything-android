package com.landvibe.beereverything.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.landvibe.beereverything.beerdata.Beer
import com.landvibe.beereverything.databinding.ItemBeerBinding

class BeerAdapter() : PagedListAdapter<Beer, BeerAdapter.BeerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(beerViewHolder: BeerViewHolder, position: Int) {
        val beer: Beer? = getItem(position)
        beerViewHolder.bind(beer)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Beer> = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldBeer: Beer, newBeer: Beer): Boolean {
                // User properties may have changed if reloaded from the DB, but ID is fixed
                return oldBeer.id == newBeer.id
            }

            override fun areContentsTheSame(oldBeer: Beer, newBeer: Beer): Boolean {
                // NOTE: if you use equals, your object must properly override Object#equals()
                // Incorrectly returning false here will result in too many animations.
                return oldBeer == newBeer
            }
        }
    }

    class BeerViewHolder(private val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer?) {
            with(binding) {
                beerItem = beer
            }
        }

    }
}