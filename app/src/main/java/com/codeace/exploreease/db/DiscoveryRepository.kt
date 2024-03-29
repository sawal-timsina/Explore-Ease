package com.codeace.exploreease.db

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codeace.exploreease.entities.UserPost
import com.codeace.exploreease.helpers.comments
import com.codeace.exploreease.helpers.placeList
import com.codeace.exploreease.workers.PostWorkers
import com.google.firebase.database.*

class DiscoveryRepository {
    private var query: DatabaseReference =
        FirebaseDatabase.getInstance().reference
    private val allPost = MutableLiveData<List<UserPost>>()

    init {
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "Inserting Data")

                PostWorkers({
                    allPost.value = it
                }, { pl, c, l, p, u ->
                    placeList = pl.toMutableList()
                    comments = c
                }).execute(dataSnapshot)

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

    fun getAllPosts(): LiveData<List<UserPost>> {
        return allPost
    }
}