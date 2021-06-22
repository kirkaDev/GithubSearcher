package com.desiredsoftware.githubsearcher.data.api

import com.desiredsoftware.githubsearcher.data.FollowersSearchResults
import com.desiredsoftware.githubsearcher.data.ProfileSearchResults
import com.desiredsoftware.githubsearcher.data.RepositoriesCollection
import com.desiredsoftware.githubsearcher.data.commit.CommitSearchResults
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchProfilesSuspend(@Header("Authorization") token: String, @Query("q") query: String): ProfileSearchResults

    @GET("users/{username}/followers")
    suspend fun getFollowersSuspend(@Header("Authorization") token: String, @Path("username") username: String): FollowersSearchResults

    @GET("users/{username}/repos")
    suspend fun getRepositoriesSuspend(@Header("Authorization") token: String, @Path("username") username: String): RepositoriesCollection

    @GET("repos/{username}/{repo}/commits")
    suspend fun getCommitsSuspend(@Header("Authorization") token: String, @Path("username") username: String, @Path("repo") repo: String): CommitSearchResults

    companion object Factory {
        fun create(baseUrl: String): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}