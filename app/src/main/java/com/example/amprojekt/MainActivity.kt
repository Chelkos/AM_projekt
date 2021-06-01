package com.example.amprojekt

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import buttons.MenuButton
import room.HighscoreDao
import room.HighscoreDatabase
import kotlin.system.exitProcess
import room.HighscoreItem
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(applicationContext, HighscoreDatabase::class.java, "gameDB").allowMainThreadQueries().build()
        gameDao = db.highscoreDao()
    }

    companion object {
        lateinit var gameDao : HighscoreDao
    }

}