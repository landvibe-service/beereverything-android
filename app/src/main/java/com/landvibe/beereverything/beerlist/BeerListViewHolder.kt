package com.landvibe.beereverything.beerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.landvibe.beereverything.R
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ActivityBeerlistBinding
import com.landvibe.beereverything.databinding.ItemBeerlistBinding
import kotlinx.android.synthetic.main.item_beerlist.view.*

class BeerListViewHolder (val binding: ItemBeerlistBinding) : RecyclerView.ViewHolder(binding.root){

    fun bindTo(beer: Beer?){
        with(binding){
            beerItem = beer
        }
    }
}
