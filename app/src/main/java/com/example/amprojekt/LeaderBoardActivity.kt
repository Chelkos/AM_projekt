package com.example.amprojekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import room.HighscoreItem

class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var adapter : LeaderBoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        adapter = LeaderBoardAdapter()
        val recycler = findViewById<RecyclerView>(R.id.highScores)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    companion object {

        fun loadFromDB() : MutableList<HighscoreItem> {
            return MainActivity.gameDao.getAll()
        }

        fun removeItem(item : HighscoreItem) {
            MainActivity.gameDao.deleteScore(item)
        }

    }

}