package com.desiredsoftware.githubsearcher.presentation.main

import com.desiredsoftware.githubsearcher.ui.activity.MainView
import moxy.MvpPresenter

class MainPresenter : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.displayMainActivity()
    }
}