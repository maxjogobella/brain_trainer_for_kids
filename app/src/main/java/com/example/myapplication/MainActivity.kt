package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.utils.Constants.EXTRA_ONBOARDING
import com.example.myapplication.utils.Constants.SHARED_PREF_ONBOARDING_NAME
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.Onboarding1Fragment
import com.example.myapplication.fragments.Onboarding2Fragment
import com.example.myapplication.fragments.Onboarding3Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        val value = getPreferences(EXTRA_ONBOARDING, 0)
        showOnBoarding(value)

    }

    private fun showOnBoarding(value : Int) {
        if (value == 100) {
            val intent = MenuActivity.newIntent(this)
            startActivity(intent)
            finish()
        } else {
            setFirstFragment()
            startFragments()
        }
    }

    private fun completeOnboarding() {
        getSharedPreferences(EXTRA_ONBOARDING, MODE_PRIVATE).edit().putInt(
            EXTRA_ONBOARDING, 100).apply()
    }

    private fun startFragments() {
        supportFragmentManager.setFragmentResultListener("fragment_onboarding",
            this) { _, bundle ->
            val nextFragment = when(bundle.getString("next")) {
                "onboarding2" -> Onboarding2Fragment()
                "onboarding3" -> Onboarding3Fragment()
                "onboarding_last" -> {
                    val intent = MenuActivity.newIntent(this)
                    startActivity(intent)
                    completeOnboarding()
                    finish()
                    null
                }
                else -> null
            }
            switchFragments(nextFragment)
        }
    }

    private fun switchFragments(nextFragment : Fragment?) {
        if (nextFragment != null) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .add(R.id.fragment_container_view, nextFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setFirstFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, Onboarding1Fragment())
            .commit()
    }

    private fun getPreferences(
        key : String,
        defaultValue : Int) : Int {
        return getSharedPreferences(SHARED_PREF_ONBOARDING_NAME,
            MODE_PRIVATE).getInt(key, defaultValue)
    }
}