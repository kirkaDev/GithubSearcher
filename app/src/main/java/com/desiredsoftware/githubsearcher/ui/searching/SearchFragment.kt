package com.desiredsoftware.githubsearcher.ui.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.githubsearcher.BuildConfig
import com.desiredsoftware.githubsearcher.R
import com.desiredsoftware.githubsearcher.data.Profile

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: AccountSearchingAdapter

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProvider(this).get(SearchViewModel::class.java)

        navController = requireParentFragment().findNavController()

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val searchView : SearchView = root.findViewById(R.id.searchView)
        val progressBar : ProgressBar = root.findViewById(R.id.progressBar)

        // To save time and call the API
        if(BuildConfig.DEBUG)
        searchView.setQuery("kirkadev", false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                progressBar.isVisible = true
                    searchViewModel.getSearchResults(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Due to calls API restriction for the unauthorized users 60 per hour (savings)
                if(!BuildConfig.DEBUG)
                searchViewModel.getSearchResults(newText)

                return false
            }
        })

        val recyclerViewSearch : RecyclerView = root.findViewById(R.id.accountSearchRecycler)

        searchViewModel.searchResultsLD.observe(viewLifecycleOwner, Observer {
            searchAdapter = AccountSearchingAdapter(it,
                object : OnClickUserListener {
                    override fun onClicked(userClicked: Profile) {
                       val action = SearchFragmentDirections.actionNavigationSearchToRepositoriesFragment(userClicked.login)
                        navController.navigate(action)
                    }
                })
            progressBar.isVisible = false
            recyclerViewSearch.adapter = searchAdapter
            recyclerViewSearch.layoutManager = GridLayoutManager(context, 3)
        })

        return root
    }
}