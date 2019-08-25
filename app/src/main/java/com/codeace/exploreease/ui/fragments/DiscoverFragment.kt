package com.codeace.exploreease.ui.fragments


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.codeace.exploreease.R
import com.codeace.exploreease.adapters.DiscoverAdapter
import com.codeace.exploreease.entities.UserPost
import com.codeace.exploreease.ui.activity.PostDetails
import com.codeace.exploreease.viewModels.DiscoverViewModel
import kotlinx.android.synthetic.main.fragment_discover.view.*
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 */
class DiscoverFragment : Fragment(), DiscoverAdapter.ItemListeners {

    private var discoverAdapter = DiscoverAdapter()
    private var discoverVM: DiscoverViewModel? = null

    override fun onFoodItemClicked(post: UserPost) {
        val intent = Intent(activity, PostDetails::class.java)
        intent.putExtra("PostDetails", post as Serializable)
        startActivity(intent)
    }

    override fun onItemDelete(post: UserPost) {
    }

    override fun onItemUpdate(post: UserPost) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_discover, container, false)

        view.discoverRecyclerView.layoutManager = LinearLayoutManager(context)
        discoverAdapter.setItemListeners = this
        view.discoverRecyclerView.adapter = discoverAdapter
        view.discoverRecyclerView.setHasFixedSize(true)

        discoverVM = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DiscoverViewModel(activity!!.application) as T
            }
        }).get(DiscoverViewModel::class.java)
        Log.d(TAG, "View Model Initialized")

        discoverVM?.getLiveData()!!.observe(this, Observer { postList ->
            Log.d(TAG, "New List : ".plus(postList.toString()))
            discoverAdapter.submitList(postList)
        })

        return view
    }

}
