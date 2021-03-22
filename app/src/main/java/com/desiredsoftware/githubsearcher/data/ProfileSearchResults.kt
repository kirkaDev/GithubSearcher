package com.desiredsoftware.githubsearcher.data

data class ProfileSearchResults(val incomplete_results: Boolean,
                                val items: List<Profile>,
                                val total_count: Int)



