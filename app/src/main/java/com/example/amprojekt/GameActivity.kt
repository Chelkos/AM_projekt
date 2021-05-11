package com.example.javap

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.javap.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import java.util.*

class GameActivity : AppCompatActivity() {
    var questionsIndexes: List<Int?> = ArrayList()
    var stage = 0
    var allQuestions: Array<String>
    var chosenQuestions = arrayOfNulls<String>(10)
    var chosenAnswers = arrayOfNulls<String>(10)
    var allAnswers: Array<String>
    var correctAnswer: String? = null
    var questionText: TextView? = null
    var feedbackText: TextView? = null
    var shuffledAnswers: List<String?>? = null
    var answerABut: Button? = null
    var answerBBut: Button? = null
    var answerCBut: Button? = null
    var answerDBut: Button? = null
    var flag = 0
    fun shuffleAnswers(answers: String?) {
        shuffledAnswers = ArrayList()
        val words = answers!!.split(";").toTypedArray()
        Log.d("T", "" + words[0])
        shuffledAnswers = Arrays.asList(*words)
        correctAnswer = words[0]
        Collections.shuffle(shuffledAnswers)
        play(stage)
    }

    val questions: Unit
        get() {
            allQuestions = resources.getStringArray(R.array.questions)
            allAnswers = resources.getStringArray(R.array.answers)
            for (i in allQuestions.indices) {
                questionsIndexes.add(i, i)
            }
            Collections.shuffle(questionsIndexes)
            for (i in 0..9) {
                chosenQuestions[i] = allQuestions[questionsIndexes[i]!!]
                chosenAnswers[i] = allAnswers[questionsIndexes[i]!!]
            }
            shuffleAnswers(chosenAnswers[0])
        }

    fun play(stage: Int) {
        questionText!!.text = chosenQuestions[stage]
        answerABut!!.text = shuffledAnswers!![0]
        answerBBut!!.text = shuffledAnswers!![1]
        answerCBut!!.text = shuffledAnswers!![2]
        answerDBut!!.text = shuffledAnswers!![3]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)
        answerABut = findViewById(R.id.answerAButton)
        answerBBut = findViewById(R.id.answerBButton)
        answerCBut = findViewById(R.id.answerCButton)
        answerDBut = findViewById(R.id.answerDButton)
        answerABut.setOnClickListener(View.OnClickListener {
            if (answerABut.getText() == correctAnswer) {
                stage++
                shuffleAnswers(allAnswers[stage])
            } else {
                feedbackText.setText("Wrong!")
            }
        })
        answerBBut.setOnClickListener(View.OnClickListener {
            if (answerBBut.getText() == correctAnswer) {
                stage++
                shuffleAnswers(allAnswers[stage])
            } else {
                feedbackText.setText("Wrong!")
            }
        })
        answerCBut.setOnClickListener(View.OnClickListener {
            if (answerCBut.getText() == correctAnswer) {
                stage++
                shuffleAnswers(allAnswers[stage])
            } else {
                feedbackText.setText("Wrong!")
            }
        })
        answerDBut.setOnClickListener(View.OnClickListener {
            if (answerDBut.getText() == correctAnswer) {
                stage++
                shuffleAnswers(allAnswers[stage])
            } else {
                feedbackText.setText("Wrong!")
            }
        })
        questions
    }
}