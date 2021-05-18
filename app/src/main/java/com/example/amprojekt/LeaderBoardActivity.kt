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
        adapter = LeaderBoardAdapter(dummyData()) //loadScoresFromDB()
        val recycler = findViewById<RecyclerView>(R.id.highScores)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    /*fun loadScoresFromDB() : MutableList<HighscoreItem> {

    }*/

    fun dummyData() : MutableList<HighscoreItem> {
        return MutableList(6) {i -> HighscoreItem(i, "Player $i", i, 0) }
    }

}