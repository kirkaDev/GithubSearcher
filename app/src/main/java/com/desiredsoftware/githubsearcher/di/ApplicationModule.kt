package com.desiredsoftware.githubsearcher.di

import com.desiredsoftware.githubsearcher.data.api.ApiClient
import com.desiredsoftware.utils.BASE_URL
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideApiClient(): ApiClient{
        return ApiClient(BASE_URL)
    }
}