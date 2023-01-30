package com.example.quotes

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.util.Random


class MainActivity : AppCompatActivity() {
    //An android application that fetches quotes form an api
    //consumes The api--https://type.fit/api/quotes
    //add a dialog to show progress of data loading
    var dialog: ProgressDialog? =null
    private lateinit var recyclerView: RecyclerView
    //add random backgrounds
    private lateinit var layoutMain:RelativeLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implementation of the request manager

        RequestManager(this@MainActivity).GetAllQuotes(listener)

        //setting up the progress dialog

        dialog= ProgressDialog(this@MainActivity)
        dialog!!.setTitle("Loading...")
        dialog!!.show()

        recyclerView=findViewById(R.id.recyclerData)

        layoutMain=findViewById(R.id.layoutmain)

        //set layout background
        //Todo***
        //set up a variety of backgrounds displayed randomly
        layoutMain.setBackgroundResource(R.drawable.bgimg)

    }

    private val listener:QuotesResponseListener= object :QuotesResponseListener{
        override fun fetched(response: List<QuotesResponse>, message: String) {
            //dismiss the progress dialog once the data is fetched
            dialog?.dismiss()

            //setting up the recycler view
            recyclerView.setHasFixedSize(true)
            //setting the number of items to appear vertically on the recyclerview
            recyclerView.layoutManager=StaggeredGridLayoutManager(1,LinearLayoutManager.VERTICAL)
            //mapping the recycler view adapter
            //i have created a custom adapter
            val adapter=QuoteslistCustomAdapter(this@MainActivity,response)
            recyclerView.adapter=adapter


        }
        override fun failed(message: String) {
           //notify the user when an an error occurs
            dialog?.dismiss()
            Toast.makeText(this@MainActivity, "Error occurred!!", Toast.LENGTH_SHORT).show()


        }


    }


}