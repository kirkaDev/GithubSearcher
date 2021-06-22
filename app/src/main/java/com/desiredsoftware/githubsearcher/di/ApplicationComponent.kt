package com.desiredsoftware.githubsearcher.di

import com.desiredsoftware.githubsearcher.ui.fragment.repositories.RepositoriesFragment
import com.desiredsoftware.githubsearcher.ui.fragment.searching.SearchFragment
import dagger.Component

@Component (modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(fragment: RepositoriesFragment)
    fun inject(fragment: SearchFragment)
}