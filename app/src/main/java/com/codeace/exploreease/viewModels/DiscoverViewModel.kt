package com.codeace.exploreease.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codeace.exploreease.db.DiscoveryRepository
import com.codeace.exploreease.entities.Post

class DiscoverViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DiscoveryRepository()

    fun getLiveData(): LiveData<List<Post>> {
        return repository.getAllPosts()
    }
}