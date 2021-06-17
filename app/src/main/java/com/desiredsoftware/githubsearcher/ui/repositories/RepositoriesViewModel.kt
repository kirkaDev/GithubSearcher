package com.desiredsoftware.githubsearcher.ui.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desiredsoftware.githubsearcher.BuildConfig
import com.desiredsoftware.githubsearcher.data.RepositoryItem
import com.desiredsoftware.githubsearcher.data.api.rx.RxApiClient
import com.desiredsoftware.utils.BASE_URL
import com.desiredsoftware.utils.DEVELOPER_PERSONAL_TOKEN
import com.desiredsoftware.utils.getRussianDateFormat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepositoriesViewModel : ViewModel() {

    private val apiClient: RxApiClient = RxApiClient(BASE_URL)

    var repositoriesResultsLD: MutableLiveData<List<RepositoryItem>> = MutableLiveData()

    fun getSearchRepositoriesResults(userNameForSearch: String) {

        val outRepos =  mutableListOf<RepositoryItem>()

        apiClient.apiService.getRepositories(DEVELOPER_PERSONAL_TOKEN, userNameForSearch)
            .flatMap { repoList -> Observable.fromIterable(repoList) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { repo ->
                    Log.d("Rx", "Running getCommits API method for the repository: ${repo.name}")
                    apiClient.apiService.getCommits(DEVELOPER_PERSONAL_TOKEN, userNameForSearch, repo.name)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            {
                                Log.d("Rx", "Commits list for the repo: ${repo.name}:")

                                if (it != null) {
                                    // Take the first commit in the request for getting the last commit date
                                    repo.last_commit = getRussianDateFormat(it.first().commit.author.date)

                                    if(BuildConfig.DEBUG) {
                                        it.forEach { commit ->
                                            Log.d(
                                                "Rx", "Commit description: ${commit.commit.message}"
                                            )
                                        }
                                    }
                                } else {
                                    repo.last_commit = "There are no any commits here"
                                }

                                outRepos.add(repo)
                            },
                            {
                                it.printStackTrace()
                            },
                            {
                                // onComplete
                                Log.d("Rx", "Running onCompleted in the getCommits API method")
                                repositoriesResultsLD.value = outRepos
                            })
                },
                { throwable ->
                    Log.d(
                        "Rx",
                        "Got throwable in the getCommits API method: ${throwable.printStackTrace()}"
                    )
                    throwable.printStackTrace()
                },
                {
                    Log.d(
                        "Rx",
                        "Running onCompleted in the getCommits API method: all followers data in observable is left"
                    )
                })
    }
}