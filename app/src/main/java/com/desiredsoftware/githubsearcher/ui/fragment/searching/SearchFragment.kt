package com.desiredsoftware.githubsearcher.ui.fragment.searching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.desiredsoftware.githubsearcher.data.Profile
import com.desiredsoftware.githubsearcher.databinding.FragmentSearchBinding
import com.desiredsoftware.githubsearcher.presentation.searching.IProfileSearchingDisplayer
import com.desiredsoftware.githubsearcher.presentation.searching.SearchingPresenter
import com.desiredsoftware.githubsearcher.ui.searching.AccountSearchingAdapter
import com.desiredsoftware.githubsearcher.ui.searching.OnClickUserListener
import com.desiredsoftware.utils.SPAN_COUNT_SEARCHING_PROFILE_RECYCLER_VIEW
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SearchFragment : MvpAppCompatFragment(), IProfileSearchingDisplayer {

    private val searchingPresenter by moxyPresenter { SearchingPresenter() }

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        navController = requireParentFragment().findNavController()
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val rootView = binding.root

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.progressBar.isVisible = true
                if (!query.isEmpty()) {
                    searchingPresenter.searchAndShowProfiles(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                binding.progressBar.isVisible = true
                if (!query.isEmpty()) {
                    searchingPresenter.searchAndShowProfiles(query)
                }
                return false
            }
        })

        return rootView
    }

    override fun showProfileList(searchResults : List<Profile>) {
        binding.progressBar.isVisible = false
        binding.profileSearchingRecyclerView.adapter = AccountSearchingAdapter(searchResults, object : OnClickUserListener {
            override fun onClicked(userClicked: Profile) {
                val action = SearchFragmentDirections.actionNavigationSearchToRepositoriesFragment(userClicked.login)
                navController.navigate(action)
            }
        })
        binding.profileSearchingRecyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT_SEARCHING_PROFILE_RECYCLER_VIEW)
    }

    override fun clearProfileList() {
        binding.profileSearchingRecyclerView.adapter = null
    }
}