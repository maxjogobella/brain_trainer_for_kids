package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.utils.Constants.EXTRA_LEVEL_KEY
import com.example.myapplication.utils.Constants.SHARED_PREF_APPLICATION_NAME
import com.example.myapplication.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMenuBinding
    private var isReady : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        binding.startGame.setOnClickListener {
            val value = getPreferences(EXTRA_LEVEL_KEY, 0)

            if (value in 1..3) {
                val intent = GameActivity.newIntent(this)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.notchooselevel), Toast.LENGTH_SHORT).show() //TODO: добавить перевод
            }
        }

        binding.levelButton.setOnClickListener {
            val dialog = Dialog(this, R.style.Theme_MyApplication)
            with(dialog) {
                window?.setBackgroundDrawable(ColorDrawable(Color.argb(50, 0, 0, 0)))
                setContentView(R.layout.level_menu)
                setCancelable(true)
            }

            val buttonEasy = dialog.findViewById<TextView>(R.id.buttonEasy)
            val buttonMedium = dialog.findViewById<TextView>(R.id.buttonMedium)
            val buttonHard = dialog.findViewById<TextView>(R.id.buttonHard)


            buttonEasy.setOnClickListener {
                commitPreferences(EXTRA_LEVEL_KEY, 1)
                isReady = true
                dialog.hide()
                Toast.makeText(this, getString(R.string.timer2), Toast.LENGTH_SHORT).show()
            }

            buttonMedium.setOnClickListener {
                commitPreferences(EXTRA_LEVEL_KEY, 2)
                isReady = true
                dialog.hide()
                Toast.makeText(this, getString(R.string.timer5), Toast.LENGTH_SHORT).show()
            }

            buttonHard.setOnClickListener {
                commitPreferences(EXTRA_LEVEL_KEY, 3)
                isReady = true
                dialog.hide()
                Toast.makeText(this, getString(R.string.timer10), Toast.LENGTH_SHORT).show()
            }

            dialog.show()
        }
    }


    private fun getPreferences(key : String, defaultValue : Int) : Int {
        return getSharedPreferences(SHARED_PREF_APPLICATION_NAME, MODE_PRIVATE).getInt(key, defaultValue)
    }

    private fun commitPreferences(key : String, value : Int) {
        getSharedPreferences(SHARED_PREF_APPLICATION_NAME, MODE_PRIVATE).edit().putInt(key,  value).apply()
    }

    companion object {
        fun newIntent(context : Context) : Intent = Intent(context, MenuActivity::class.java)
    }
}