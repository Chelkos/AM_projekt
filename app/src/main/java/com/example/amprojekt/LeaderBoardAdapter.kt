package com.example.amprojekt

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import room.HighscoreItem

class LeaderBoardAdapter(private var data : MutableList<HighscoreItem>) : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = data.size

}