package com.example.quotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteslistCustomAdapter (val context: Context,val list: List<QuotesResponse>)
 : RecyclerView.Adapter<QuotesViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
    //inflate the layout
        val layout=LayoutInflater.from(context).inflate(R.layout.quotes_holder,parent,false)
        return  QuotesViewHolder(layout)
    }


    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {

        //display data in the relevant holders
        holder.textviewquote.text=list.get(position).text
        holder.textauthor.text=list.get(position).author
        var likecount: Int=0
        holder.likeBtn.setOnClickListener(){
            likecount++
            holder.likeBtn.text= likecount.toString()
        }

    }


    override fun getItemCount(): Int {

        return list.size
    }



}

//custom viwholder
 class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
     //find the items in the quotes holder
     var textviewquote: TextView=itemView.findViewById(R.id.TextQuote)
 var textauthor:TextView=itemView.findViewById(R.id.TextAuthor)

    var likeBtn:Button=itemView.findViewById(R.id.likeBtn)

 }