package com.desiredsoftware.githubsearcher.data.api

import com.desiredsoftware.githubsearcher.data.FollowersSearchResults
import com.desiredsoftware.githubsearcher.data.ProfileSearchResults
import com.desiredsoftware.githubsearcher.data.RepositoriesCollection
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun search(@Query("q") query: String): Observable<ProfileSearchResults>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Observable<FollowersSearchResults>

    @GET("users/{username}/repos")
    fun getRepositories(@Path("username") username: String): Observable<RepositoriesCollection>

    companion object Factory {

        fun create(baseUrl: String): ApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
            return retrofit.create(ApiService::class.java);
        }
    }

}