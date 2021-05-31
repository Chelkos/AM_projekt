package com.example.amprojekt

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import room.HighscoreDao
import room.HighscoreDatabase
import kotlin.system.exitProcess
import room.HighscoreItem
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var resultView : TextView
    private var name = "DEFAULT_NAME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultView = findViewById(R.id.resultView)
        if(savedInstanceState != null) {
            savedInstanceState.apply {
                resultView.text = getString("RESULT_TEXT")
                name = getString("NAME")!!
            }
        } else {
            resultView.text = ""
        }
        val newGameButton = findViewById<Button>(R.id.newgameBut)
        val leaderButton = findViewById<Button>(R.id.leaderBut)
        val exitButton = findViewById<Button>(R.id.exitBut)
        val db = Room.databaseBuilder(applicationContext, HighscoreDatabase::class.java, "gameDB").allowMainThreadQueries().build()
        gameDao = db.highscoreDao()

        val builder: AlertDialog.Builder = let {
            AlertDialog.Builder(it)
        }
        builder.apply {
            setTitle("Enter your name:")
            val view = layoutInflater.inflate(R.layout.dialog_view, null)
            val nameInput = view.findViewById<EditText>(R.id.player_name)
            setView(view)
            setPositiveButton("OK") { _, _ ->
                val text = nameInput.text.toString()
                if(text != "") {
                    name = text
                    val intent = Intent(applicationContext, GameActivity::class.java)
                    startActivityForResult(intent, 1)
                } else {
                    Toast.makeText(applicationContext, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
            }
        }
        val dialog: AlertDialog = builder.create()

        newGameButton.setOnClickListener {
            dialog.show()
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
            val item = HighscoreItem(0, name, score, Date().time)
            if(gameDao.getPlayer(name).isEmpty()) {
                gameDao.insertNewPlayer(item)
            } else {
                gameDao.updateScore(name, score, System.currentTimeMillis())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString("NAME", name)
            putString("RESULT_TEXT", resultView.text.toString())
        }
    }

    companion object {
        lateinit var gameDao : HighscoreDao
    }

}