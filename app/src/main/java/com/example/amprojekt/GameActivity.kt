package com.example.amprojekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import java.util.*

class GameActivity : AppCompatActivity() {

    private var stage = 0
    private var helpAvailable = true
    lateinit var questionsIndexes : List<Int>
    lateinit var allQuestions : Array<String>
    lateinit var allAnswers : Array<String>
    lateinit var chosenQuestions : Array<String>
    lateinit var chosenAnswers : Array<String>
    lateinit var correctAnswer : String
    lateinit var questionText : TextView
    lateinit var shuffledAnswers : List<String>
    lateinit var buttonArray : Array<Button>
    lateinit var messageView : TextView
    private var flag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        questionText = findViewById(R.id.questionText)
        messageView = findViewById(R.id.messageView)
        buttonArray = arrayOf(findViewById(R.id.answerAButton), findViewById(R.id.answerBButton), findViewById(R.id.answerCButton),
                findViewById(R.id.answerDButton))
        val listener = View.OnClickListener { it ->
            val button = it as Button
            val text = button.text
            if (text == correctAnswer) {
                if(stage < 10) {
                    messageView.text = "Dobrze!"
                    Handler().postDelayed({
                        messageView.text = ""
                    }, 1*1000)
                    loadStage()
                } else {
                    messageView.text = "WYGRANA"
                    Handler().postDelayed({
                        endGame(10)
                    }, 3*1000)
                }
            } else if(text != "") {
                messageView.text = "Źle!"
                Handler().postDelayed({
                    messageView.text = "KONIEC GRY"
                }, 1*1000)
                Handler().postDelayed({
                    endGame(stage - 1)
                                      }, 3*1000)
                buttonArray.forEach { it.setOnClickListener {  } }
            }
        }
        buttonArray.forEach { it.setOnClickListener(listener) }
        findViewById<Button>(R.id.removeAnswersButton).setOnClickListener {
            removeAnswers()
            helpAvailable = false
            it.setOnClickListener { }
        }
        if(savedInstanceState!=null)
        {
            Log.d("S","Odczytano")
            if(savedInstanceState.getStringArray("CHOSEN_QUESTIONS")!=null)
            {
                chosenQuestions = savedInstanceState.getStringArray("CHOSEN_QUESTIONS")!!

            }
            if(savedInstanceState.getInt("CURRENT_STAGE")!=null)
            {
                stage = savedInstanceState.getInt("CURRENT_STAGE") -1

            }
            if( savedInstanceState.getStringArray("CHOSEN_ANSWERS")!=null)
            {
                chosenAnswers = savedInstanceState.getStringArray("CHOSEN_ANSWERS")!!

            }
            if(savedInstanceState.getBoolean("HELP_AVAILABLE")!=null)
            {
                helpAvailable=savedInstanceState.getBoolean("HELP_AVAILABLE")

            }
            shuffleAnswers(chosenAnswers[stage])
            if(savedInstanceState.getString("CURRENT_CORRECT_ANSWER")!=null)
            {
                correctAnswer=savedInstanceState.getString("CURRENT_CORRECT_ANSWER")!!

            }
            flag =1;


        }




    }
    override fun onStart() {
        super.onStart()

        if(flag ==1)
        {
            Log.d("S","Sprawdzono flagę"+flag)
            questionText.text = chosenQuestions[stage]
            buttonArray.forEachIndexed { index, button -> button.text = shuffledAnswers[index] }
            flag =0

        }
        else
        {
            getQuestions()
            loadStage()
        }

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

    private fun loadStage() {
        shuffleAnswers(chosenAnswers[stage])
        questionText.text = chosenQuestions[stage]
        buttonArray.forEachIndexed { index, button -> button.text = shuffledAnswers[index] }
        stage++
    }

    private fun removeAnswers() {
        val pos = arrayOf(0, 1, 2, 3)
        val r = Random(System.currentTimeMillis())
        var j : Int
        var tmp : Int
        var c = 2
        for (i in 3 downTo 0) {
            j = r.nextInt(i + 1)
            tmp = pos[i]
            pos[i] = pos[j]
            pos[j] = tmp
        }
        for (i in 0..3) {
            if(buttonArray[pos[i]].text != correctAnswer) {
                buttonArray[pos[i]].text = ""
                c--
            }
            if(c == 0) break
        }
    }

    private fun endGame(score : Int) {
        intent = Intent()
        intent.putExtra("score", score)
        setResult(1, intent)
        finish()
    }
    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        Log.d("S","Zapisano" )
        outState.putStringArray("CHOSEN_ANSWERS",chosenAnswers)
        outState.putStringArray("CHOSEN_QUESTIONS",chosenQuestions)

        outState.putString("CURRENT_CORRECT_ANSWER",correctAnswer)
        outState.putBoolean("HELP_AVAILABLE",helpAvailable)
        outState.putInt("CURRENT_STAGE",stage)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {



    }
}