package com.example.myapplication.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.utils.Constants
import com.example.myapplication.enums.Difficulty
import kotlin.random.nextInt

class GameViewModel : ViewModel() {

    private val correctAnswerLiveData = MutableLiveData<Int>()
    private val secondsLiveData = MutableLiveData<Int>()
    private val isTimerFinishedLiveData = MutableLiveData(false)
    private val questionLiveData = MutableLiveData<String>()
    private var timer : CountDownTimer? = null

        fun generateQuestion(difficulty: Difficulty): List<Int> {
            val (a, b) = List(2) { kotlin.random.Random.nextInt(difficulty.range) }
            val (operationSymbol, operationFunction) = difficulty.operations.random()
            val correctAnswer = operationFunction(a, b)

            questionLiveData.value = "$a $operationSymbol $b"
            correctAnswerLiveData.value = correctAnswer

            val answers = mutableSetOf(correctAnswer)

            while (answers.size < 4) {
                answers.add(
                    correctAnswer + (-1..1).random() * (answers.size) * kotlin.random.Random.nextInt(
                        difficulty.wrongAnswerMultiplier
                    )
                )
            }
            return answers.shuffled()
        }

    fun startTimer(difficulty: Difficulty) {
        timer = object : CountDownTimer(difficulty.timer, Constants.SPEED_TIMER) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                secondsLiveData.value = seconds
            }

            override fun onFinish() {
                isTimerFinishedLiveData.value = true
            }
        }
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
        isTimerFinishedLiveData.value = true
    }

    fun getCorrectAnswer(): LiveData<Int> = correctAnswerLiveData
    fun getSeconds(): LiveData<Int> = secondsLiveData
    fun isTimerFinished(): LiveData<Boolean> = isTimerFinishedLiveData
    fun getQuestion(): LiveData<String> = questionLiveData

}