package com.example.amprojekt

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LeaderBoardAdapter(input : List<String>) : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    private lateinit var data : List<PlayerRecord>

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

    data class PlayerRecord(
        val nick : String,
        val date : Long,
        val result : Int
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = data.size

}