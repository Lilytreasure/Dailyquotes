package com.example.quotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    //An android application that fetches quotes form an api
    //consumes The api--https://type.fit/api/quotes


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implementation of the request manager

        RequestManager(this@MainActivity).GetAllQuotes(listener)

    }

    private val listener:QuotesResponseListener= object :QuotesResponseListener{
        override fun fetched(response: List<QuotesResponse>, message: String) {


        }
        override fun failed(message: String) {


        }


    }


}