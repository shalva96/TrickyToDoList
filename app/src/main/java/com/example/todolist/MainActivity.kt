package com.example.todolist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todolist.DataClass.DataModel
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.sharedPref.SharedPref


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPref
    private val dataModel: DataModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        sharedPref = SharedPref(this)
        if (sharedPref.getValue()) {
            openFrag(HomePage.newInstance(), R.id.placeHolder)
        }else {
            sharedPref.saveValue(true)
            openFrag(OnboardingFragment.newInstance(), R.id.placeHolder)
        }

        dataModel.openFragEmptyFragment.observe(this) {
            openFrag(EmptyHomeFragment.newInstance(), R.id.placeHolder)
        }

        dataModel.newTaskFromHomePage.observe(this) {
            openFrag(AddNewTaskFragment.newInstance(), R.id.placeHolder)
        }



    }



    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, f)
            .commit()
    }


}