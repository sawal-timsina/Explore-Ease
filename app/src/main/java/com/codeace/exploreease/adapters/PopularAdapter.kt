package com.codeace.exploreease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codeace.exploreease.R
import com.codeace.exploreease.entities.PlaceLocation
import com.codeace.exploreease.helpers.listImage
import kotlinx.android.synthetic.main.popular_list_item.view.*

class PopularAdapter : ListAdapter<PlaceLocation, PopularAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface ItemListeners {
        fun onItemClicked(post: PlaceLocation)
    }

    var setItemListeners: ItemListeners? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.popular_list_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(getItem(position), setItemListeners!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            post: PlaceLocation, itemListeners: ItemListeners
        ) {
            itemView.setOnClickListener {
                itemListeners.onItemClicked(post)
            }

            Glide.with(itemView.context).load(listImage[adapterPosition])
                .placeholder(R.drawable.imageplaceholder).centerCrop()
                .into(itemView.media_image)
            itemView.textView.text = post.locationName
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlaceLocation>() {
            override fun areItemsTheSame(oldItem: PlaceLocation, newItem: PlaceLocation): Boolean {
                return oldItem.locationId === newItem.locationId
            }

            override fun areContentsTheSame(
                oldItem: PlaceLocation,
                newItem: PlaceLocation
            ): Boolean {
                return oldItem.locationId == newItem.locationId &&
                        oldItem.locationName == newItem.locationName &&
                        oldItem.lat == newItem.lat &&
                        oldItem.long == newItem.long &&
                        oldItem.description == newItem.description
            }
        }
    }
}