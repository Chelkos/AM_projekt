package com.example.amprojekt

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper.getMainLooper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.*
import androidx.fragment.app.Fragment
import buttons.GameButton
import java.util.*

class GameFragment : Fragment(){
    private var stage = 0
    private var helpAvailable = true
    private var mProgress = 0

    lateinit var questionText : TextView
    lateinit var messageView : TextView
    lateinit var buttonArray : Array<GameButton>
    lateinit var timer : ProgressBar
    lateinit var questionsIndexes : List<Int>
    lateinit var allQuestions : Array<String>
    lateinit var allAnswers : Array<String>
    lateinit var chosenQuestions : Array<String>
    lateinit var chosenAnswers : Array<String>
    lateinit var correctAnswer : String
    lateinit var shuffledAnswers : ArrayList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        questionText = view!!.findViewById(R.id.questionText)
        messageView = view!!.findViewById(R.id.messageView)
        buttonArray = arrayOf(view!!.findViewById(R.id.answerAButton), view!!.findViewById(R.id.answerBButton), view!!.findViewById(R.id.answerCButton),
            view!!.findViewById(R.id.answerDButton))
        timer = view!!.findViewById(R.id.timer)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
             var fiftyparams = view!!.findViewById<GameButton>(R.id.removeAnswersButton).layoutParams
            fiftyparams.height = 120
            fiftyparams.width = 240
            view!!.findViewById<GameButton>(R.id.removeAnswersButton).changeTextSize(35f)
            questionText.textSize = 20f
            buttonArray.forEach {
               var params = it.layoutParams
                params.height = (params.height * 0.8f).toInt()

            }
        }
        timer.min = 0
        timer.max = TIME_LIMIT.toInt()

        val listener = View.OnClickListener { it ->
            val button = it as GameButton
            val text = button.getContent()

            if (text == correctAnswer) {
                stage++
                if(stage < 10) {
                    messageView.text = "Dobrze!"
                    Handler(getMainLooper()).postDelayed({
                        messageView.text = ""
                    }, 1*1000)
                    loadStage(true)
                } else {
                    timer.animation = null
                    messageView.text = "WYGRANA"
                    Handler(getMainLooper()).postDelayed({
                        endGame(10)
                    }, 3*1000)
                }
            } else if(text != "") {
                timer.animation = null
                messageView.text = "Źle!"
                Handler(getMainLooper()).postDelayed({
                    messageView.text = "KONIEC GRY"
                }, 1*1000)
                Handler(getMainLooper()).postDelayed({
                    endGame(stage)
                }, 3*1000)
                buttonArray.forEach { it.setOnClickListener {  } }
            }
        }
        buttonArray.forEach { it.setOnClickListener(listener) }
        view!!.findViewById<GameButton>(R.id.removeAnswersButton).setOnClickListener {
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
                stage = getInt("CURRENT_STAGE")
                mProgress = getInt("PROGRESS")
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
        questionText.text = chosenQuestions[stage]
        if(nextStage) {
            mProgress = timer.max
            shuffleAnswers(chosenAnswers[stage])
        }
        buttonArray.forEachIndexed { index, button -> button.changeText(shuffledAnswers[index]) }

        val anim = object : Animation() {
            private val initProgress = mProgress

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                super.applyTransformation(interpolatedTime, t)
                val progress = initProgress * (1 - interpolatedTime)
                mProgress = progress.toInt()
                timer.progress = mProgress
            }
        }
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { }

            override fun onAnimationEnd(animation: Animation?) {
                messageView.text = "CZAS MINĄŁ"
                Handler().postDelayed({
                    messageView.text = "KONIEC GRY"
                }, 1*1000)
                Handler().postDelayed({
                    endGame(stage)
                }, 3*1000)
            }

            override fun onAnimationRepeat(animation: Animation?) { }
        })
        anim.duration = mProgress.toLong()
        timer.startAnimation(anim)
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
            if(buttonArray[pos[i]].getContent() != correctAnswer) {
                buttonArray[pos[i]].changeText("")
                c--
            }
            if(c == 0) break
        }
    }

    private fun endGame(score : Int) {
        var intent =  Intent(this@GameFragment.context, GameActivity::class.java)
        intent.putExtra("score", score)
        activity!!.setResult(1, intent)
        activity!!.finish()
    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.apply {
            putStringArray("CHOSEN_ANSWERS", chosenAnswers)
            putStringArray("CHOSEN_QUESTIONS", chosenQuestions)
            putString("CURRENT_CORRECT_ANSWER", correctAnswer)
            putStringArrayList("SHUFFLED_ANSWERS", shuffledAnswers)
            putBoolean("HELP_AVAILABLE", helpAvailable)
            putInt("CURRENT_STAGE", stage)
            putInt("PROGRESS", mProgress)
        }
    }

    companion object {
        const val TIME_LIMIT = 10000L
    }
}