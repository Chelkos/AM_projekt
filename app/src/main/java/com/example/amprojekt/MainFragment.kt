package com.example.amprojekt

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import buttons.MenuButton
import room.HighscoreItem
import java.util.*
import kotlin.system.exitProcess

class MainFragment : Fragment() {

    private lateinit var resultView : TextView
    private var name = "DEFAULT_NAME"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        resultView = view!!.findViewById(R.id.resultView)
        if(savedInstanceState != null) {
            savedInstanceState.apply {
                resultView.text = getString("RESULT_TEXT")
                name = getString("NAME")!!
            }
        } else {
            resultView.text = ""
        }
        val newGameButton = view!!.findViewById<MenuButton>(R.id.newgameBut)
        val leaderButton = view!!.findViewById<MenuButton>(R.id.leaderBut)
        val exitButton = view!!.findViewById<MenuButton>(R.id.exitBut)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            var params = view!!.findViewById<LinearLayout>(R.id.buttonList).layoutParams
            params.width = (params.width * 0.8f).toInt()
            view!!.findViewById<TextView>(R.id.projectTitle).textSize = 50f
            newGameButton.changeTextSize(50f)
            leaderButton.changeTextSize(50f)
            exitButton.changeTextSize(50f)
        }


        val builder: AlertDialog.Builder = let {
            AlertDialog.Builder(context)
        }
        builder.apply {
            setTitle("Enter your name:")
            val view = layoutInflater.inflate(R.layout.dialog_view, null)
            val nameInput = view.findViewById<EditText>(R.id.player_name)
            setView(view)
            setPositiveButton("OK") { _, _ ->
                val text = nameInput.text.toString().trim()
                if(text != "") {
                    name = text
                    val intent = Intent(context, GameActivity::class.java)
                    startActivityForResult(intent, 1)
                } else {
                    Toast.makeText(context, "Name cannot be empty!", Toast.LENGTH_SHORT).show()
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
            val intent = Intent(context, LeaderBoardActivity::class.java)
            startActivity(intent)
        }
        exitButton.setOnClickListener {
            activity?.finish()
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
            if(MainActivity.gameDao.getPlayer(name).isEmpty()) {
                MainActivity.gameDao.insertNewPlayer(item)
            } else {
                MainActivity.gameDao.updateScore(name, score, System.currentTimeMillis())
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

}