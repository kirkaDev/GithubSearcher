package com.desiredsoftware.githubsearcher.ui.searching

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desiredsoftware.githubsearcher.R
import com.desiredsoftware.githubsearcher.data.Profile

class AccountSearchingAdapter(private val searchResults: List<Profile>,
                              private val onClickUserListener: OnClickUserListener,) :
        RecyclerView.Adapter<AccountSearchingAdapter.ViewHolder>() {


    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountSearchingAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.user_info_item, parent, false)

        context = parent.context

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AccountSearchingAdapter.ViewHolder, position: Int) {
        holder.usernameTextView?.text = searchResults[position].login

        holder.followersNumber?.text = "Followers: " + searchResults[position].followersNumber.toString()

        holder.avatarImageView?.let {
            Glide.with(context)
                    .load(searchResults[position].avatar_url)
                    .placeholder(R.mipmap.ic_github)
                    .error(R.mipmap.ic_github)
                    .override(200, 200)
            .centerCrop().into(it)
        }

        holder.avatarImageView?.setOnClickListener {
            onClickUserListener.onClicked(searchResults[position])
        }

    }

    override fun getItemCount(): Int {
        return searchResults.size
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