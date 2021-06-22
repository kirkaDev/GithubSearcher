package com.desiredsoftware.githubsearcher.data.api

class ApiClient (baseUrl : String) {
    val apiService = ApiService.create(baseUrl)
}