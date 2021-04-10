package com.desiredsoftware.githubsearcher.ui.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.githubsearcher.R

class RepositoriesFragment : Fragment() {

    companion object {
        fun newInstance() = RepositoriesFragment()
    }

    val args: RepositoriesFragmentArgs by navArgs()

    private lateinit var viewModel: RepositoriesViewModel
    private lateinit var repositoriesAdapter: RepositoriesInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(RepositoriesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_repositories, container, false)

        val recyclerViewRepositories : RecyclerView = root.findViewById(R.id.repositoriesRecyclerView)

        viewModel.getSearchRepositoriesResults(args.loginForRepoInfo)

        viewModel.repositoriesResultsLD.observe(viewLifecycleOwner, Observer {
            repositoriesAdapter = RepositoriesInfoAdapter(it)

            recyclerViewRepositories.adapter = repositoriesAdapter
            recyclerViewRepositories.layoutManager = LinearLayoutManager(context)
        })

        return root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}