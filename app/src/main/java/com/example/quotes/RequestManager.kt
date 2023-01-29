package com.example.quotes

import android.content.Context
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RequestManager (mContext: Context){

    var retrofit=Retrofit.Builder()
        .baseUrl("https://type.fit/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //getting all quotes
    fun GetAllQuotes(listener: QuotesResponseListener){

        val call=retrofit.create(CallQuotes::class.java).callQuotes()
        call.enqueue(object : Callback<List<QuotesResponse>>{
            override fun onResponse(
                call: Call<List<QuotesResponse>>,
                response: Response<List<QuotesResponse>>
            ) {
                //if response is successful\
                if (response.isSuccessful){
                    println("Request Successfull")
                }

            }

            override fun onFailure(call: Call<List<QuotesResponse>>, t: Throwable) {
               println("Request failed")
            }

        })

    }



    private interface  CallQuotes{
        @GET("api/quotes")
        fun callQuotes():Call<List<QuotesResponse>>

    }

}