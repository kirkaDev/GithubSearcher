package com.desiredsoftware.githubsearcher.ui.repositories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.githubsearcher.R
import com.desiredsoftware.githubsearcher.data.RepositoriesCollection

class RepositoriesInfoAdapter(private val repositoriesList: RepositoriesCollection) :
        RecyclerView.Adapter<RepositoriesInfoAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesInfoAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.repository_info_item, parent, false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoriesInfoAdapter.ViewHolder, position: Int) {
        holder.repositoryNameTextView?.text = repositoriesList[position].name

        if (repositoriesList[position].description!=null)
            holder.repositoryDescription?.text = repositoriesList[position].description
        else
        holder.repositoryDescription?.text = R.string.description_is_empty.toString()

        // TODO: Get last commit name and date
        holder.lastCommitTextView?.text = "Will be implemented"

        holder.defaultBranch?.text = repositoriesList[position].default_branch
        holder.forksCountTextView?.text = repositoriesList[position].forks_count.toString()
        holder.starsCountTextView?.text = repositoriesList[position].stargazers_count.toString()
        holder.defaultLanguageTextView?.text = repositoriesList[position].language
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var repositoryNameTextView: TextView? = null
        var repositoryDescription: TextView? = null
        var lastCommitTextView: TextView? = null
        var defaultBranch: TextView? = null
        var forksCountTextView: TextView? = null
        var starsCountTextView: TextView? = null
        var defaultLanguageTextView: TextView? = null

        init {
            repositoryNameTextView = itemView.findViewById(R.id.repositoryNameTextView)
            repositoryDescription = itemView.findViewById(R.id.repositoryDescription)
            lastCommitTextView = itemView.findViewById(R.id.lastCommitTextView)
            defaultBranch = itemView.findViewById(R.id.defaultBranch)
            forksCountTextView = itemView.findViewById(R.id.forksCountTextView)
            starsCountTextView = itemView.findViewById(R.id.starsCountTextView)
            defaultLanguageTextView = itemView.findViewById(R.id.defaultLanguageTextView)
        }
    }
}