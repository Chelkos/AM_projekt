package com.example.amprojekt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.amprojekt.R
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newGameButton = findViewById<Button>(R.id.newgameBut)
        val leaderButton = findViewById<Button>(R.id.leaderBut)
        val exitButton = findViewById<Button>(R.id.exitBut)
        newGameButton.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            startActivity(intent)
        }
        leaderButton.setOnClickListener {
            val intent = Intent(applicationContext, LeaderBoardActivity::class.java)
            startActivity(intent)
        }
        exitButton.setOnClickListener {
            finish()
            exitProcess(0)
        }
    }
}