package com.example.amprojekt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.amprojekt.R
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var resultView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultView = findViewById(R.id.resultView)
        resultView.text = ""
        val newGameButton = findViewById<Button>(R.id.newgameBut)
        val leaderButton = findViewById<Button>(R.id.leaderBut)
        val exitButton = findViewById<Button>(R.id.exitBut)
        newGameButton.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            startActivityForResult(intent, 1)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val score = data.getIntExtra("score", 0)
            if(score < 10) {
                resultView.text = "Your result: $score correct answers"
            } else {
                resultView.text = "You won!"
            }
        }
    }

}