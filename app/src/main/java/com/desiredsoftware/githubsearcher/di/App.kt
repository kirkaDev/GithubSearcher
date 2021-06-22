package com.desiredsoftware.githubsearcher.di

import android.app.Application

open class App : Application() {

    companion object { lateinit var appComponent: ApplicationComponent }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.create()
    }
}