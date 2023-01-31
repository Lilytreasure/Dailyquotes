package com.example.quotes

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainActivity : AppCompatActivity() {
    //An android application that fetches quotes form an api
    //consumes The api--https://type.fit/api/quotes

    //Todo **
    //offline data caching


    var dialog: ProgressDialog? =null
    private lateinit var recyclerView: RecyclerView
    //add random backgrounds
    private lateinit var layoutMain:RelativeLayout
    //network textview
    private lateinit var  textNetwork: TextView

    private lateinit var adapter: QuoteslistCustomAdapter
    private lateinit var  refreshLayout: SwipeRefreshLayout


    @RequiresApi(Build.VERSION_CODES.M)
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

        //hide the notify textview  when  network connectivity is detected
        textNetwork=findViewById(R.id.textNetwork)
        textNetwork.isVisible = !isOnline(this)

        //pull to refresh

        refreshLayout=findViewById(R.id.RefreshLayout)

       //Refreshing the recyclerView on pull
        refreshLayout.setOnRefreshListener {

            refreshLayout.isRefreshing=false

            RequestManager(this@MainActivity).GetAllQuotes(listener)
            textNetwork.isVisible = !isOnline(this)

        }



//end of onCreate Method
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
             adapter=QuoteslistCustomAdapter(this@MainActivity,response)
            recyclerView.adapter=adapter


        }
        override fun failed(message: String) {
           //notify the user when an an error occurs
            dialog?.dismiss()
            Toast.makeText(this@MainActivity, "Error occurred!!", Toast.LENGTH_LONG).show()

        }


    }



    //A function to check internet connectivity state

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }



}