package com.landvibe.beereverything.view.beerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.databinding.ItemBeerlistBinding

class BeerListAdapter(private val itemClickListener: OnItemClickListener) :
    PagedListAdapter<Beer, BeerListViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: BeerListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
        holder.itemView.setOnClickListener {
            item?.let { itemClickListener.onItemClick(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerlistBinding.inflate(inflater, parent, false)
        return BeerListViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean =
                oldItem.name == newItem.name && newItem.image_url == oldItem.image_url
        }

        @BindingAdapter("imageFromUrl")
        @JvmStatic
        fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(beer: Beer)
    }
}