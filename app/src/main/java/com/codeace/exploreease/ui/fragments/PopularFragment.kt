package com.codeace.exploreease.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codeace.exploreease.R
import com.codeace.exploreease.adapters.PopularAdapter
import com.codeace.exploreease.entities.PlaceLocation
import com.codeace.exploreease.ui.activity.MapActivity
import kotlinx.android.synthetic.main.fragment_popular.view.*
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 */
class PopularFragment : Fragment(), PopularAdapter.ItemListeners {
    override fun onItemClicked(post: PlaceLocation) {
        val intent = Intent(activity, MapActivity::class.java)
        intent.putExtra("PlaceLocation", post as Serializable)

        startActivity(intent)
    }

    private var popularAdapter = PopularAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular, container, false)

        view.popularRecyclerView.layoutManager = LinearLayoutManager(activity)
        popularAdapter.setItemListeners = this
        view.popularRecyclerView.adapter = popularAdapter
        view.popularRecyclerView.setHasFixedSize(true)

        return view
    }

}// Required empty public constructor
