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
                FilterItems(
                    "https://firebasestorage.googleapis.com/v0/b/visit-nepal-2020-ba341.appspot.com/o/location%2FGarden%20of%20Dreams.jpg?alt=media&token=eb93231a-1816-4551-86c9-e67711aceecc",
                    "Fun Places"
                ),
                FilterItems(
                    "https://firebasestorage.googleapis.com/v0/b/visit-nepal-2020-ba341.appspot.com/o/location%2FNagarjun%20Forest%20Reserve.jpg?alt=media&token=0e74953c-b91b-4f3f-b92f-32ef1efe4418",
                    "Hotels"
                ),
                FilterItems(
                    "https://firebasestorage.googleapis.com/v0/b/visit-nepal-2020-ba341.appspot.com/o/location%2FPokhara.png?alt=media&token=55352497-543f-4c38-8c6f-09bcfe74181f",
                    "Trekking"
                )
            )
        )
        view.filterRecyclerView.adapter = filterAdapter
        view.filterRecyclerView.setHasFixedSize(true)

        return view
    }


}