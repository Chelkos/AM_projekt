package com.example.amprojekt

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import room.HighscoreItem

class LeaderboardFragment : Fragment() {

    private lateinit var adapter : LeaderBoardAdapter
    private lateinit var savedData : MutableList<HighscoreItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
        val recycler = view!!.findViewById<RecyclerView>(R.id.highScores)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view!!.findViewById<TextView>(R.id.titleView).textSize = 25f
            recycler.layoutParams.height = 600
        }
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
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

}