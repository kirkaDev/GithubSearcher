package com.desiredsoftware.githubsearcher.presentation.repositories

import com.desiredsoftware.githubsearcher.data.RepositoryItem
import com.desiredsoftware.githubsearcher.data.api.api.ApiClient
import com.desiredsoftware.utils.BASE_URL
import com.desiredsoftware.utils.DEVELOPER_PERSONAL_TOKEN
import com.desiredsoftware.utils.getRussianDateFormat
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

@InjectViewState
class RepositoriesPresenter : MvpPresenter<IRepositoryDisplayer>() {

    private val apiClient: ApiClient = ApiClient(BASE_URL)

    fun getSearchRepositoriesResults(userNameForSearch: String) {

        var outRepos : List<RepositoryItem> = emptyList()

        presenterScope.launch {

            val deferreds = mutableListOf<Deferred<String>>()

            outRepos = apiClient.apiService.getRepositoriesSuspend(DEVELOPER_PERSONAL_TOKEN, userNameForSearch)

            outRepos.forEach { repo ->
                val deferred = async {
                        getRussianDateFormat(apiClient.apiService.getCommitsSuspend(DEVELOPER_PERSONAL_TOKEN, userNameForSearch, repo.name)
                            .first().commit.author.date).also { repo.last_commit = it }
                }
                deferreds.add(deferred)
            }
            deferreds.awaitAll()
            viewState.showRepositories(outRepos)
        }
    }
}

interface IRepositoryDisplayer: MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun showRepositories(repositories : List<RepositoryItem>)
}