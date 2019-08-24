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
import com.codeace.exploreease.entities.UserPost
import kotlinx.android.synthetic.main.discover_list_item.view.*

class DiscoverAdapter : ListAdapter<UserPost, DiscoverAdapter.ViewHolder>(DIFF_CALLBACK) {
    interface ItemListeners {
        fun onFoodItemClicked(post: UserPost)
        fun onItemDelete(post: UserPost)
        fun onItemUpdate(post: UserPost)
    }

    var visibility = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var setItemListeners: ItemListeners? = null

    fun getDataAt(position: Int): UserPost {
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
            post: UserPost, visible: Boolean, itemListeners: ItemListeners
        ) {
            Glide.with(itemView.context).load(post.post[adapterPosition].imageUri).centerCrop()
                .placeholder(R.drawable.imageplaceholder).into(itemView.media_image)
            itemView.title_text.text = post.user.userName
            itemView.subtitle_text.text = post.post[adapterPosition].locationName
            itemView.action_like.text = post.likes.size.toString()
            itemView.action_like.setOnClickListener {

            }

            itemView.action_comment.text = post.comments.size.toString()
            itemView.action_comment.setOnClickListener {
                itemListeners.onFoodItemClicked(post)
            }

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

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserPost>() {
            override fun areItemsTheSame(oldItem: UserPost, newItem: UserPost): Boolean {
                return oldItem.post === newItem.post
            }

            override fun areContentsTheSame(oldItem: UserPost, newItem: UserPost): Boolean {
                return oldItem.user == newItem.user &&
                        oldItem.post == newItem.post &&
                        oldItem.likes == newItem.likes &&
                        oldItem.comments == newItem.comments
            }
        }
    }
}