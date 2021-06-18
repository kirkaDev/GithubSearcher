package com.desiredsoftware.githubsearcher.ui.fragment.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.desiredsoftware.githubsearcher.data.RepositoryItem
import com.desiredsoftware.githubsearcher.databinding.FragmentRepositoriesBinding
import com.desiredsoftware.githubsearcher.presentation.repositories.IRepositoryDisplayer
import com.desiredsoftware.githubsearcher.presentation.repositories.RepositoriesPresenter
import com.desiredsoftware.githubsearcher.ui.repositories.RepositoriesInfoAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class RepositoriesFragment : MvpAppCompatFragment(), IRepositoryDisplayer {

    companion object {
        fun newInstance() = RepositoriesFragment()
    }

    private var _binding : FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    private val args: RepositoriesFragmentArgs by navArgs()

    private val repositoriesPresenter by moxyPresenter { RepositoriesPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.progressBar.isVisible = true
        repositoriesPresenter.getSearchRepositoriesResults(args.loginForRepoInfo)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun showRepositories(repositories: List<RepositoryItem>) {
        binding.progressBar.isVisible = false
        binding.repositoriesRecyclerView.adapter = RepositoriesInfoAdapter(repositories)
        binding.repositoriesRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}