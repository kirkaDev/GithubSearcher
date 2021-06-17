package com.desiredsoftware.githubsearcher.data.api.rx

import com.desiredsoftware.githubsearcher.data.FollowersSearchResults
import com.desiredsoftware.githubsearcher.data.ProfileSearchResults
import com.desiredsoftware.githubsearcher.data.RepositoriesCollection
import com.desiredsoftware.githubsearcher.data.commit.CommitSearchResults
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RxApiService {


    @GET("search/users")
    fun searchProfiles(@Header("Authorization") token: String, @Query("q") query: String): Observable<ProfileSearchResults>

    @GET("users/{username}/followers")
    fun getFollowers(@Header("Authorization") token: String, @Path("username") username: String): Observable<FollowersSearchResults>

    @GET("users/{username}/repos")
    fun getRepositories(@Header("Authorization") token: String, @Path("username") username: String): Observable<RepositoriesCollection>

    @GET("repos/{username}/{repo}/commits")
    fun getCommits(@Header("Authorization") token: String, @Path("username") username: String, @Path("repo") repo: String): Observable<CommitSearchResults>

    companion object Factory {

        fun create(baseUrl: String): RxApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
            return retrofit.create(RxApiService::class.java)
        }
    }

}