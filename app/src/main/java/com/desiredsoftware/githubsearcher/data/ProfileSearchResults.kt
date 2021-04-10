package com.desiredsoftware.githubsearcher.data

data class ProfileSearchResults(val incomplete_results: Boolean = false,
                                var items: List<Profile> = emptyList(),
                                val total_count: Int = 0)



