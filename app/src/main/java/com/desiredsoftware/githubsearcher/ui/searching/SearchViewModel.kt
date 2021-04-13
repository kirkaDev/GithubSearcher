package com.desiredsoftware.githubsearcher.ui.searching
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.githubsearcher.data.Profile
import com.desiredsoftware.githubsearcher.data.api.ApiClient
import com.desiredsoftware.utils.BASE_URL
import com.desiredsoftware.utils.DEVELOPER_PERSONAL_TOKEN
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private val apiClient: ApiClient = ApiClient(BASE_URL)

    var searchResultsLD: MutableLiveData<List<Profile>> = MutableLiveData()

    fun getSearchResults(userNameForSearch: String) {

        val searchResults =  mutableListOf<Profile>()

        apiClient.apiService.search(DEVELOPER_PERSONAL_TOKEN, userNameForSearch)
            .map(Function { t -> t.items })
            .flatMap { profileList -> Observable.fromIterable(profileList) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    profile ->
                    Log.d("Rx", "Running getFollowers API method for the user: ${profile.login}")
                    apiClient.apiService.getFollowers(DEVELOPER_PERSONAL_TOKEN, profile.login)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            {
                                Log.d("Rx", "Followers list for the user ${profile.login}:")
                                it.forEach {
                                        follower ->  Log.d("Rx", "${follower.login}")
                                }
                                profile.followersNumber=it.size
                                searchResults.add(profile)
                            },
                            {
                                it.printStackTrace()
                            },
                            {
                                // onComplete
                                Log.d("Rx", "Running onCompleted in the getFollowers API method")
                                searchResultsLD.value = searchResults
                            })
                },
                {
                    throwable ->
                    Log.d("Rx", "Got throwable in the getFollowers API method: ${throwable.printStackTrace()}")
                    throwable.printStackTrace()
                },
                {
                    Log.d("Rx", "Running onCompleted in the search API method: all followers data in observable is left")
                })
    }
}