package com.codeace.exploreease.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codeace.exploreease.R
import com.codeace.exploreease.adapters.FilterAdapter
import com.codeace.exploreease.entities.FilterItems
import kotlinx.android.synthetic.main.fragment_filter.view.*

/**
 * A simple [Fragment] subclass.
 */
class FilterFragment : Fragment(), FilterAdapter.ItemListeners {
    override fun onItemClicked(post: FilterItems) {
    }

    private var filterAdapter = FilterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_filter, container, false)

        view.filterRecyclerView.layoutManager = LinearLayoutManager(activity)
        filterAdapter.setItemListeners = this
        filterAdapter.submitList(
            listOf(
                FilterItems("", ""),
                FilterItems("", ""),
                FilterItems("", "")
            )
        )
        view.filterRecyclerView.adapter = filterAdapter
        view.filterRecyclerView.setHasFixedSize(true)

        return view
    }


}