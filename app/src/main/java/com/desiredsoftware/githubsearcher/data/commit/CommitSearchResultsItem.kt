package com.desiredsoftware.githubsearcher.data.commit

import com.desiredsoftware.githubsearcher.data.Profile

data class CommitSearchResultsItem(
    val sha: String,
    val node_id: String,
    val commit: Commit,
    val url: String,
    val html_url: String,
    val comments_url: String,
    val author: Profile,
    val committer: Profile,
    val parents: List<Any>,
)