package com.desiredsoftware.githubsearcher.presentation.login

import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@InjectViewState
class LoginPresenter : MvpPresenter<ILoginHandler>() {

    fun makeLogin(providerId : String)
    {
        viewState.openOAuthProvider(providerId)
    }
}

interface ILoginHandler: MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun openOAuthProvider(providerId: String)
}