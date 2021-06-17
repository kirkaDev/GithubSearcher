package com.desiredsoftware.githubsearcher.presentation.searching

import android.util.Log
import com.desiredsoftware.githubsearcher.data.Profile
import com.desiredsoftware.githubsearcher.data.api.rx.RxApiClient
import com.desiredsoftware.githubsearcher.ui.fragment.searching.IProfileSearchingDisplayer
import com.desiredsoftware.utils.BASE_URL
import com.desiredsoftware.utils.DEVELOPER_PERSONAL_TOKEN
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter


@InjectViewState
class SearchingPresenter : MvpPresenter<IProfileSearchingDisplayer>() {

    private val apiClient: RxApiClient = RxApiClient(BASE_URL)

    fun searchAndShowProfiles(userNameForSearch : String)
    {
        val searchResults =  mutableListOf<Profile>()

        apiClient.apiService.searchProfiles(DEVELOPER_PERSONAL_TOKEN, userNameForSearch)
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
                                viewState.showProfileList(searchResults)
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