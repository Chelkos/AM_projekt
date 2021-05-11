package com.example.javap

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var newGame: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newGame = findViewById(R.id.newgameBut)
        newGame.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            startActivity(intent)
        })
    }
}