package com.desiredsoftware.githubsearcher.ui.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.githubsearcher.data.ProfileSearchResults
import com.desiredsoftware.githubsearcher.data.RepositoriesCollection
import com.desiredsoftware.githubsearcher.data.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoriesViewModel : ViewModel() {


    private val baseUrl = "https://api.github.com/"
    val apiClient: ApiClient = ApiClient(baseUrl)

    var repositoriesResultsLD: MutableLiveData<RepositoriesCollection> = MutableLiveData()
    lateinit var searchResultsTemp: RepositoriesCollection

    fun getSearchRepositoriesResults(userNameForSearch: String) {
        apiClient.apiService.getRepositories(userNameForSearch)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                searchResultsTemp = result
                repositoriesResultsLD.value = searchResultsTemp
                Log.d("Result", "Repositories searching results: Found ${result.size} repos")
            }, { error ->
                error.printStackTrace()
            })
    }
}