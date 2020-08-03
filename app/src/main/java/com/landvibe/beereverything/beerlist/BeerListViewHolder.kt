package com.landvibe.beereverything.beerlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.landvibe.beereverything.R
import com.landvibe.beereverything.data.BeerList

class BeerListViewHolder (parent:ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_beerlist, parent, false)) {
    private val nameView = itemView.findViewById<TextView>(R.id.beer_name)
    private val idView = itemView.findViewById<TextView>(R.id.beer_id)
    private var beerList : BeerList? = null


    fun bindTo(beerList: BeerList?){
        this.beerList = beerList
        nameView.text = beerList?.name
        idView.text = beerList?.id.toString()
    }

    fun clear(){
        nameView.text = null
    }


}
