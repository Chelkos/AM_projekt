package com.example.amprojekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import java.util.*

class GameActivity : AppCompatActivity() {

    private var stage = 0
    private var helpAvailable = true
    private var timeLeft : Long = 10000


    lateinit var countDown : CountDownTimer
    lateinit var questionText : TextView
    lateinit var messageView : TextView
    lateinit var buttonArray : Array<Button>
    lateinit var timer : ProgressBar
    lateinit var questionsIndexes : List<Int>
    lateinit var allQuestions : Array<String>
    lateinit var allAnswers : Array<String>
    lateinit var chosenQuestions : Array<String>
    lateinit var chosenAnswers : Array<String>
    lateinit var correctAnswer : String
    lateinit var shuffledAnswers : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        questionText = findViewById(R.id.questionText)
        messageView = findViewById(R.id.messageView)
        buttonArray = arrayOf(findViewById(R.id.answerAButton), findViewById(R.id.answerBButton), findViewById(R.id.answerCButton),
                findViewById(R.id.answerDButton))
        timer = findViewById(R.id.timer)



        val listener = View.OnClickListener { it ->
            val button = it as Button
            val text = button.text

            countDown.cancel()
            if (text == correctAnswer) {
                if(stage < 10) {

                    messageView.text = "Dobrze!"
                    Handler().postDelayed({
                        messageView.text = ""
                    }, 1*1000)
                    loadStage(true)
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


        if(savedInstanceState != null) {
            savedInstanceState.apply {
                chosenAnswers = getStringArray("CHOSEN_ANSWERS")!!
                chosenQuestions = getStringArray("CHOSEN_QUESTIONS")!!
                correctAnswer = getString("CURRENT_CORRECT_ANSWER")!!
                shuffledAnswers = getStringArrayList("SHUFFLED_ANSWERS")!!
                helpAvailable = getBoolean("HELP_AVAILABLE")
                stage = getInt("CURRENT_STAGE") - 1
                timeLeft = getLong("TIME_LEFT")
            }
            loadStage(false)
        } else {
            getQuestions()
            loadStage(true)
        }
    }

    private fun shuffleAnswers(answers: String) {
        val words = answers.split(";")
        shuffledAnswers = words as ArrayList<String>
        correctAnswer = words[0]
        shuffledAnswers.shuffle()
    }

    private fun getQuestions() {
        allQuestions = resources.getStringArray(R.array.questions)
        allAnswers = resources.getStringArray(R.array.answers)
        questionsIndexes = MutableList(allQuestions.size) { i -> i }
        Collections.shuffle(questionsIndexes)
        chosenQuestions = Array(10) {i -> allQuestions[questionsIndexes[i]] }
        chosenAnswers = Array(10) {i -> allAnswers[questionsIndexes[i]] }
    }

    private fun loadStage(nextStage : Boolean) {
      var countdownInterval : Long = 150
        timer.setProgress(((timer.max-timer.min)*(timeLeft.toFloat()/10000)).toInt())
        if(nextStage) {
            timeLeft = 10000
            shuffleAnswers(chosenAnswers[stage])
        }

        countDown =   object:CountDownTimer(timeLeft, countdownInterval){

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = timeLeft - countdownInterval
                Log.d("PB",""+((timer.max-timer.min)*(timeLeft.toFloat()/10000)).toInt())
                timer.setProgress(((timer.max-timer.min)*(timeLeft.toFloat()/10000)).toInt())
            }

            override fun onFinish() {
                Log.d("PB","FINISH ")
                timer.setProgress(timer.min)
                messageView.text = "KONIEC CZASU!"
                endGame(stage-1)
            }
        }.start()


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
        countDown.cancel()
        outState.apply {
            putStringArray("CHOSEN_ANSWERS",chosenAnswers)
            putStringArray("CHOSEN_QUESTIONS",chosenQuestions)
            putString("CURRENT_CORRECT_ANSWER",correctAnswer)
            putStringArrayList("SHUFFLED_ANSWERS", shuffledAnswers)
            putBoolean("HELP_AVAILABLE",helpAvailable)
            putInt("CURRENT_STAGE",stage)
            putLong("TIME_LEFT",timeLeft)
        }
    }

}