package com.desiredsoftware.githubsearcher.ui.searching

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.githubsearcher.data.ProfileSearchResults
import com.desiredsoftware.githubsearcher.data.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    lateinit var accountSearchResults : ProfileSearchResults

    val apiClient : ApiClient = ApiClient()


    var searchResults : MutableLiveData<ProfileSearchResults> = MutableLiveData()

    fun getSearchResults(userNameForSearch: String ): LiveData<ProfileSearchResults>
    {
        apiClient.apiService.search(userNameForSearch).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    searchResults.value =  result
                    Log.d("Result", "Account searching results: Found ${result.items.size} accounts")
                }, { error ->
                    error.printStackTrace()
                })

        return searchResults
    }
}