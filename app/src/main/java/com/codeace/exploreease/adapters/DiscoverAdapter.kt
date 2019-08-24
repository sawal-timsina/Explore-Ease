package com.codeace.exploreease.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codeace.exploreease.R
import com.codeace.exploreease.entities.Post
import kotlinx.android.synthetic.main.discover_list_item.view.*

class DiscoverAdapter : ListAdapter<Post, DiscoverAdapter.ViewHolder>(DIFF_CALLBACK) {
    interface ItemListeners {
        fun onFoodItemClicked(post: Post)
        fun onItemDelete(post: Post)
        fun onItemUpdate(post: Post)
    }

    var visibility = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var setItemListeners: ItemListeners? = null

    fun getDataAt(position: Int): Post {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.discover_list_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(getItem(position), visibility, setItemListeners!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            post: Post, visible: Boolean, itemListeners: ItemListeners
        ) {
            Glide.with(itemView.context).load(post.imageUri).centerCrop()
                .placeholder(R.drawable.imageplaceholder).into(itemView.media_image)
            itemView.title_text.text = post.userName.toString()
            itemView.subtitle_text.text = post.location.toString()
            itemView.action_like.text = post.likes?.size.toString()
            itemView.action_like.setOnClickListener { }

            itemView.action_comment.text = post.comments?.size.toString()
            itemView.action_comment.setOnClickListener { }

            itemView.setOnClickListener {
                itemListeners.onFoodItemClicked(post)
            }
            if (visible) {
                val popupMenu = PopupMenu(itemView.context, itemView.optionButton)
                popupMenu.menuInflater.inflate(R.menu.discovery_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_delete -> {
                            itemListeners.onItemDelete(post)
                        }
                        R.id.action_update -> {
                            itemListeners.onItemUpdate(post)
                        }
                    }
                    true
                }
                itemView.optionButton.setOnClickListener {
                    popupMenu.show()
                }
                itemView.setOnLongClickListener {
                    popupMenu.show()
                    true
                }
            } else {
                itemView.optionButton.visibility = View.GONE
            }
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.key === newItem.key
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.key == newItem.key &&
                        oldItem.userName == newItem.userName &&
                        oldItem.userAvatar == newItem.userAvatar &&
                        oldItem.description == newItem.description &&
                        oldItem.dataTime == newItem.dataTime &&
                        oldItem.imageUri == newItem.imageUri &&
                        oldItem.location == newItem.location &&
                        oldItem.likes == newItem.likes &&
                        oldItem.comments == newItem.comments
            }
        }
    }
}