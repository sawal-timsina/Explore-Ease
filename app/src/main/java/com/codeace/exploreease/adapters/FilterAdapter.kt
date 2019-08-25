package com.codeace.exploreease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codeace.exploreease.R
import com.codeace.exploreease.entities.FilterItems
import kotlinx.android.synthetic.main.filter_list_item.view.*

class FilterAdapter : ListAdapter<FilterItems, FilterAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface ItemListeners {
        fun onItemClicked(post: FilterItems)
    }

    var setItemListeners: ItemListeners? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(getItem(position), setItemListeners!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            post: FilterItems, itemListeners: ItemListeners
        ) {
            itemView.setOnClickListener {
                itemListeners.onItemClicked(post)
            }

            Glide.with(itemView.context).load(post.imageUrl)
                .placeholder(R.drawable.imageplaceholder).centerCrop()
                .into(itemView.media_image)
            itemView.textView.text = post.name
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FilterItems>() {
            override fun areItemsTheSame(oldItem: FilterItems, newItem: FilterItems): Boolean {
                return oldItem.name === newItem.name
            }

            override fun areContentsTheSame(
                oldItem: FilterItems,
                newItem: FilterItems
            ): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.imageUrl == newItem.imageUrl
            }
        }
    }
}