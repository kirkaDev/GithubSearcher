package com.desiredsoftware.githubsearcher.ui.fragment.searching

import com.desiredsoftware.githubsearcher.data.Profile
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IProfileSearchingDisplayer: MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun showProfileList(searchResults : List<Profile>)
}