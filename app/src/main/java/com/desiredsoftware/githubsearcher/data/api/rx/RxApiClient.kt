package com.desiredsoftware.githubsearcher.data.api.rx

class RxApiClient(baseUrl : String) {
    val apiService = RxApiService.create(baseUrl)
}