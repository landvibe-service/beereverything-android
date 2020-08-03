package com.landvibe.beereverything.beerlist

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
//import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.landvibe.beereverything.beerdetail.BeerDetailActivity
import com.landvibe.beereverything.data.BeerList

//DataSource.Factory & LiveData Sample
class BeerListAdapter(val context: Context) : PagedListAdapter<BeerList, BeerListViewHolder>(DIFF_CALLBACK){

    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: BeerListViewHolder, position: Int) {
        val item = getItem(position)

        if(item == null)
            holder.clear()
        else
            holder.bindTo(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, item!!.id)

            val intent = Intent(context, BeerDetailActivity::class.java)
            intent.putExtra("beer_id", item.id)
            Log.d(TAG, item.id.toString())
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerListViewHolder {
        return BeerListViewHolder(parent)
    }


    companion object {
        private const val TAG = "BeerListAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BeerList>(){
            override fun areItemsTheSame(oldItem: BeerList, newItem: BeerList): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BeerList, newItem: BeerList): Boolean =
                oldItem == newItem
        }
    }
}