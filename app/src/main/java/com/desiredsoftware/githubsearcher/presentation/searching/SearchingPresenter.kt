package com.desiredsoftware.githubsearcher.presentation.searching
import com.desiredsoftware.githubsearcher.data.Profile
import com.desiredsoftware.githubsearcher.data.api.ApiClient
import com.desiredsoftware.utils.DEVELOPER_PERSONAL_TOKEN
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.presenterScope
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import javax.inject.Inject

@InjectViewState
class SearchingPresenter @Inject constructor(
    private val apiClient: ApiClient) : MvpPresenter<IProfileSearchingDisplayer>() {

    fun searchAndShowProfiles(userNameForSearch: String) {

        viewState.clearProfileList()

        var listProfiles: List<Profile> = emptyList()

        presenterScope.launch {
            val deferreds = mutableListOf<Deferred<Int>>()

            listProfiles = apiClient.apiService.searchProfilesSuspend(
                DEVELOPER_PERSONAL_TOKEN,
                userNameForSearch
            ).items

                listProfiles.forEach { profile ->
                    val deferred = async {
                        apiClient.apiService.getFollowersSuspend(
                            DEVELOPER_PERSONAL_TOKEN,
                            profile.login
                        ).size.also { followersNumber ->
                            profile.followersNumber = followersNumber
                        }
                    }
                    deferreds.add(deferred)
                }

            deferreds.awaitAll()
            viewState.showProfileList(listProfiles)
        }
    }
}

interface IProfileSearchingDisplayer : MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun showProfileList(searchResults: List<Profile>)

    @StateStrategyType(AddToEndStrategy::class)
    fun clearProfileList()
}

