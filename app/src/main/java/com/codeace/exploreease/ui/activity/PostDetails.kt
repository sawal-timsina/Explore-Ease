package com.codeace.exploreease.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codeace.exploreease.R
import com.codeace.exploreease.entities.UserPost
import kotlinx.android.synthetic.main.post_details.*

class PostDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_details)

        val userPost: UserPost = intent.extras!!.get("PostDetails") as UserPost

        Glide.with(this).load(userPost.post.imageUri).centerCrop()
            .placeholder(R.drawable.imageplaceholder).into(media_image)

        action_like.text = userPost.likes.size.toString()
        action_comment.text = userPost.comments.size.toString()

        actionBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        recycler.divider = null
        val list: MutableList<String> = mutableListOf()
        userPost.comments.forEach {
            list.add(it.comment)
        }

        recycler.adapter = ArrayAdapter(
            this,
            R.layout.list_items, list
        )
    }
}