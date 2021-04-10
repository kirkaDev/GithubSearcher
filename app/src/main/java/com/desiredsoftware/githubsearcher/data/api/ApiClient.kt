package com.desiredsoftware.githubsearcher.data.api

class ApiClient(baseUrl : String) {
    val apiService = ApiService.Factory.create(baseUrl)
}