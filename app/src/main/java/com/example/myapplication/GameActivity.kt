package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.utils.Constants.EXTRA_LEVEL_KEY
import com.example.myapplication.utils.Constants.EXTRA_MAX_VALUE
import com.example.myapplication.utils.Constants.SHARED_PREF_APPLICATION_NAME
import com.example.myapplication.databinding.ActivityGameBinding
import com.example.myapplication.enums.Difficulty
import com.example.myapplication.model.UserInfo
import com.example.myapplication.viewmodels.GameViewModel

class GameActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding
    private lateinit var viewModel : GameViewModel
    private var counter : Int = 0
    private var inCorrect : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        initApp()
    }

    private fun initApp() {
        initAnswersToButtons()
        initQuestions()
        startTimer()
        updateTimer()
    }

    private fun startTimer() {
        val difficulty = when (getPreferences(EXTRA_LEVEL_KEY, 0)) {
            1 -> Difficulty.EASY
            2 -> Difficulty.MEDIUM
            else -> Difficulty.HARD
        }
        binding.timer.max = (difficulty.timer / 1000).toInt()
        viewModel.startTimer(difficulty)
    }

   private fun initQuestions() {
       setQuestion()
       setTextIntoOptions()
   }


   private fun setQuestion() {
       viewModel.getQuestion().observe(this) {question ->
           binding.question.text = question
       }
   }

   @SuppressLint("SetTextI18n")
   private fun updateTimer() {
       viewModel.isTimerFinished().observe(this) {isFinishing ->
           if (isFinishing) {
               showDialog()
           }
       }

       viewModel.getSeconds().observe(this) {seconds ->
           val totalSeconds = binding.timer.max
           val elapsedSeconds = totalSeconds - seconds
           val progressPercent = (elapsedSeconds.toDouble() / totalSeconds.toDouble() * 100).toInt()
           binding.timer.progress = elapsedSeconds
           binding.timerPercent.text = "$progressPercent%"
       }
   }

   private fun setTextIntoOptions() {
       val list : List<Int> = when(getPreferences(EXTRA_LEVEL_KEY, 0))   {
           1 -> {
               viewModel.generateQuestion(Difficulty.EASY)
           }
           2 -> {
               viewModel.generateQuestion(Difficulty.MEDIUM)
           } else -> {
               viewModel.generateQuestion(Difficulty.HARD)
           }
       }

       binding.option1.text = list[0].toString()
       binding.option2.text = list[1].toString()
       binding.option3.text = list[2].toString()
       binding.option4.text = list[3].toString()
   }

   @SuppressLint("SetTextI18n")
   private fun initAnswersToButtons() {
       var correctAnswer = ""
       viewModel.getCorrectAnswer().observe(this) {answer ->
           correctAnswer = answer.toString()
       }

       val optionClickListener = View.OnClickListener { view ->
           val optionText = (view as TextView).text
           if (correctAnswer != optionText) {
               inCorrect++
               binding.attempts.text = "$counter / $inCorrect"
               if (inCorrect >= 3) {
                   viewModel.stopTimer()
               }
           }
           else {
               counter++
               binding.attempts.text = "${this.counter} / $inCorrect"
               initQuestions()
           }
       }

       binding.option1.setOnClickListener(optionClickListener)
       binding.option2.setOnClickListener(optionClickListener)
       binding.option3.setOnClickListener(optionClickListener)
       binding.option4.setOnClickListener(optionClickListener)
   }

   @SuppressLint("SetTextI18n")
   private fun showDialog() {
       val dialog = Dialog(this, R.style.Theme_MyApplication)
       with(dialog) {
           window?.setBackgroundDrawable(
               ColorDrawable(Color.argb(50, 0, 0, 0)))
           setContentView(R.layout.popup_status)
           setCancelable(true)
       }

       val result = dialog.findViewById<TextView>(R.id.my_result)
       val maxResultView = dialog.findViewById<TextView>(R.id.my_max_result)
       result.text = "${getString(R.string.your_result)} $counter"

       val maxSavedResult = getUserInfo().maxValue

       if (counter >= maxSavedResult) {
           getSharedPreferences(SHARED_PREF_APPLICATION_NAME,
               MODE_PRIVATE).edit().putInt(EXTRA_MAX_VALUE, counter).apply()
       }

       maxResultView.text = "${getString(R.string.max_result)} $maxSavedResult"
       startAgain(dialog)
       dialog.show()
   }

   private fun getPreferences(
       key : String,
       defaultValue : Int) : Int {
       return getSharedPreferences(
           SHARED_PREF_APPLICATION_NAME, MODE_PRIVATE).getInt(key, defaultValue)
   }


  private fun getUserInfo() : UserInfo {
       val maxValue = getPreferences(EXTRA_MAX_VALUE, 0)
        val level = getPreferences(EXTRA_LEVEL_KEY, 0)
       return UserInfo(maxValue, level)
   }


   private fun startAgain(dialog : Dialog) {
       val button = dialog.findViewById<TextView>(R.id.startAgain)
       val close = dialog.findViewById<ImageView>(R.id.close)

       button.setOnClickListener {
           counter = 0
           inCorrect = 0
           initQuestions()
           startTimer()
           dialog.hide()
       }

       close.setOnClickListener {
           val intent = MenuActivity.newIntent(this)
           startActivity(intent)
           getSharedPreferences(SHARED_PREF_APPLICATION_NAME, MODE_PRIVATE).edit().putInt(
               EXTRA_LEVEL_KEY, 0).apply()
           finish()
       }

   }

    companion object {
        fun newIntent(context : Context) : Intent = Intent(context, GameActivity::class.java)
    }
}