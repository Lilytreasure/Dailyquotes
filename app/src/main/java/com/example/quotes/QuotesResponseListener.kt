package com.example.quotes

interface QuotesResponseListener {

    fun fetched(response: List<QuotesResponse>,message :String)
    fun failed(message: String)

}