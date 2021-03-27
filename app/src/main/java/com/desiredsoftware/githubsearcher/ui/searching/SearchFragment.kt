package com.desiredsoftware.githubsearcher.ui.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.githubsearcher.R

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: AccountSearchingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val searchView : SearchView = root.findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.getSearchResults(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (searchView.query.length > 0) {
                    searchViewModel.getSearchResults(newText)
                }
                return false
            }
        })


        val recyclerViewSearch : RecyclerView = root.findViewById(R.id.accountSearchRecycler)

        searchViewModel.searchResults.observe(viewLifecycleOwner, Observer {
            searchAdapter = AccountSearchingAdapter(it)
            recyclerViewSearch.adapter = searchAdapter
            recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        })

        return root
    }
}