package com.landvibe.beereverything.view.beerlist

import androidx.recyclerview.widget.RecyclerView
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ItemBeerlistBinding

class BeerListViewHolder (private val binding: ItemBeerlistBinding) : RecyclerView.ViewHolder(binding.root){
    fun bindTo(beer: Beer?){
        with(binding){
            beerItem = beer
        }
    }
}