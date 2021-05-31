package com.example.amprojekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import room.HighscoreItem

class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var adapter : LeaderBoardAdapter
    private lateinit var savedData : MutableList<HighscoreItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        if(savedInstanceState != null) {
            savedInstanceState.apply {
                val list = getStringArrayList("SAVED_DATA")
                var args : List<String>
                if(list != null) {
                    savedData = MutableList(list.size) { i ->
                        args = list[i].split(";")
                        HighscoreItem(0, args[0], args[1].toInt(), args[2].toLong())
                    }
                }
            }
        } else {
            savedData = MainActivity.gameDao.getAll()
        }
        adapter = LeaderBoardAdapter(savedData)
        val recycler = findViewById<RecyclerView>(R.id.highScores)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            val data = adapter.getData()
            val list = ArrayList<String>()
            var str : String
            data.forEach {
                str = it.name + ";"
                str += it.highscore.toString() + ";"
                str += it.date.toString()
                list.add(str)
            }
            putStringArrayList("SAVED_DATA", list)
        }
    }

    companion object {

        fun removeItem(item : HighscoreItem) {
            MainActivity.gameDao.deleteScore(item)
        }

    }

}