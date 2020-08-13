package com.landvibe.beereverything.beerlist

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ItemBeerlistBinding

//DataSource.Factory & LiveData Sample
class BeerListAdapter(val context: Context) : PagedListAdapter<Beer, BeerListViewHolder>(DIFF_CALLBACK){
    lateinit var listener : OnItemClickListener


    interface OnItemClickListener{
        fun onItemClick(view: View, Id: Int){

        }
    }

    override fun onBindViewHolder(holder: BeerListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)

        holder.itemView.setOnClickListener {
            listener.onItemClick(it, item!!.id)
            Toast.makeText(context, "start", Toast.LENGTH_SHORT).show()
/*
            val intent = Intent(context, BeerDetailActivity::class.java)
            intent.putExtra("beer_id", clickedId)
            Log.d(TAG, "clickedId: $clickedId")
            context.startActivity(intent)
 */
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerListViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val binding = ItemBeerlistBinding.inflate(inflater, parent, false)
        return BeerListViewHolder(binding)
    }

    companion object {
        private const val TAG = "BeerListAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Beer>(){
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean =
               oldItem == newItem
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}