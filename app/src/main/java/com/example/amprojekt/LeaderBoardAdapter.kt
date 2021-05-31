package com.example.amprojekt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import room.HighscoreItem
import java.text.SimpleDateFormat
import java.util.*

class LeaderBoardAdapter(private var data : MutableList<HighscoreItem>) : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.GERMANY)

    init {
        sortByScore()
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val playerText : TextView = view.findViewById(R.id.player)
        val dateText : TextView = view.findViewById(R.id.date)
        val score : TextView = view.findViewById(R.id.score)

        init {
            view.setOnLongClickListener {
                LeaderBoardActivity.removeItem(data[layoutPosition])
                data.removeAt(layoutPosition)
                this@LeaderBoardAdapter.notifyItemRemoved(layoutPosition)
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playerText.text = data[position].name
        holder.dateText.text = dateFormat.format(Date(data[position].date))
        holder.score.text = data[position].highscore.toString()
    }

    override fun getItemCount() = data.size

    fun getData() = data

    private fun sortByScore() = data.sortByDescending { it.highscore }

}