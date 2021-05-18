package com.example.amprojekt

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*

class GameActivity : AppCompatActivity() {

    private var stage = 0
    lateinit var questionsIndexes : List<Int>
    lateinit var allQuestions : Array<String>
    lateinit var allAnswers : Array<String>
    lateinit var chosenQuestions : Array<String>
    lateinit var chosenAnswers : Array<String>
    lateinit var correctAnswer : String
    lateinit var questionText : TextView
    lateinit var shuffledAnswers : List<String>
    lateinit var answerABut : Button
    lateinit var answerBBut : Button
    lateinit var answerCBut : Button
    lateinit var answerDBut : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        questionText = findViewById(R.id.questionText)
        answerABut = findViewById(R.id.answerAButton)
        answerBBut = findViewById(R.id.answerBButton)
        answerCBut = findViewById(R.id.answerCButton)
        answerDBut = findViewById(R.id.answerDButton)
        val listener = View.OnClickListener {
            val button = it as Button
            if (button.text == correctAnswer) {
                stage++
                Toast.makeText(applicationContext, "Dobrze!", Toast.LENGTH_SHORT).show()
                shuffleAnswers(chosenAnswers[stage])
                loadStage(stage)
            } else {
                Toast.makeText(applicationContext, "Źle!", Toast.LENGTH_SHORT).show()
            }
        }
        answerABut.setOnClickListener(listener)
        answerBBut.setOnClickListener(listener)
        answerCBut.setOnClickListener(listener)
        answerDBut.setOnClickListener(listener)
        getQuestions()
        loadStage(stage)
    }

    private fun shuffleAnswers(answers: String) {
        shuffledAnswers = ArrayList()
        val words = answers.split(";").toTypedArray()
        Log.d("T", "" + words[0])
        shuffledAnswers = listOf(*words)
        correctAnswer = words[0]
        Collections.shuffle(shuffledAnswers)
    }

    private fun getQuestions() {
        allQuestions = resources.getStringArray(R.array.questions)
        allAnswers = resources.getStringArray(R.array.answers)
        questionsIndexes = MutableList(allQuestions.size) { i -> i }
        Collections.shuffle(questionsIndexes)
        chosenQuestions = Array(10) {i -> allQuestions[questionsIndexes[i]] }
        chosenAnswers = Array(10) {i -> allAnswers[questionsIndexes[i]] }
        shuffleAnswers(chosenAnswers[0])
    }

    private fun loadStage(stage: Int) {
        questionText.text = chosenQuestions[stage]
        answerABut.text = shuffledAnswers[0]
        answerBBut.text = shuffledAnswers[1]
        answerCBut.text = shuffledAnswers[2]
        answerDBut.text = shuffledAnswers[3]
    }

}