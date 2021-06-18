package com.desiredsoftware.githubsearcher.presentation.profile

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@InjectViewState
class ProfilePresenter : MvpPresenter<IProfileInfoDisplayer>() {

    private var user: FirebaseUser? = null

    private var userInfo: HashMap<String, String?> = HashMap()

    fun setProfileInfo() {
        user = Firebase.auth.currentUser

        if (user != null) {
            user?.let { loggedUser ->
                userInfo["displayName"] = loggedUser.displayName
                userInfo["email"] = loggedUser.email
                userInfo["photoUrl"] = loggedUser.photoUrl.toString()

            viewState.showProfileInfo(userInfo)
            }
        }
        else {
            viewState.showLoginFragment()
        }
    }
}

interface IProfileInfoDisplayer: MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun showProfileInfo(userInfo : Map<String, String?>)

    @StateStrategyType(AddToEndStrategy::class)
    fun showLoginFragment()
}