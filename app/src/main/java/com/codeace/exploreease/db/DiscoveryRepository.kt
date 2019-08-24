package com.codeace.exploreease.db

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codeace.exploreease.entities.Post
import com.google.firebase.database.*

class DiscoveryRepository {
    private var query: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child("userUploads").child("posts")
    private val allPost = MutableLiveData<List<Post>>()

    init {
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "Inserting Data")

                Log.d("Posts", dataSnapshot.toString())

                Log.d(TAG, "Data Inserted")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(
                    "FirebaseQueryLiveData",
                    "Can't listen to query ",
                    databaseError.toException()
                )
            }
        })
    }

    fun getAllPosts(): LiveData<List<Post>> {
        return allPost
    }
}