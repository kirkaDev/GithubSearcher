package com.desiredsoftware.githubsearcher.ui.searching

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.desiredsoftware.githubsearcher.R
import com.desiredsoftware.githubsearcher.data.ProfileSearchResults

class AccountSearchingAdapter(private val searchResults: ProfileSearchResults) :
        RecyclerView.Adapter<AccountSearchingAdapter.ViewHolder>() {


    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountSearchingAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.user_info_item, parent, false)

        context = parent.context

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountSearchingAdapter.ViewHolder, position: Int) {
        //holder.avatarImageView?.setImageResource(R.drawable.ic_menu_search_24)
        holder.usernameTextView?.text = searchResults.items[position].login

        // TODO: Implement
        holder.followersNumber?.text = "Here will be the follower's number"

        holder.avatarImageView?.let {
            Glide.with(context)
                    .load(searchResults.items[position].avatar_url)
                    .placeholder(R.mipmap.ic_github)
                    .error(R.mipmap.ic_github)
                    .override(200, 200)
            .centerCrop().into(it)
        }

    }

    override fun getItemCount(): Int {
        return searchResults.items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatarImageView: ImageView? = null
        var usernameTextView: TextView? = null
        var followersNumber: TextView? = null

        init {
            avatarImageView = itemView.findViewById(R.id.avatarImageView)
            usernameTextView = itemView.findViewById(R.id.usernameTextView)
            followersNumber = itemView.findViewById(R.id.followersNumber)
        }
    }
}