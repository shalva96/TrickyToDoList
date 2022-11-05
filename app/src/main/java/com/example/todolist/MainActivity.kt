package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    object Constant {
        const val MY_SETTINGS = "examination"
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, HomePage.newInstance())
            .commit()
    }
/**
    private fun onboardingPage() {
        val sp = getSharedPreferences(
            Constant.MY_SETTINGS,
            Context.MODE_PRIVATE
        )
        // проверяем, первый ли раз открывается программа
        val hasVisited = sp.getBoolean("hasVisited", false)

        if (!hasVisited) {
            // выводим нужную активность
            intent =  Intent(this, Onboarding::class.java)
            startActivity(intent)
            val e: SharedPreferences.Editor = sp.edit()
            e.putBoolean("hasVisited", true)
            e.commit() // не забудьте подтвердить изменения
        }
    }
**/

}