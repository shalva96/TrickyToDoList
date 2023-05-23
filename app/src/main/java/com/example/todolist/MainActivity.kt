package com.example.todolist

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.Adapter.ToDoListAdapter
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Dao
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.Db.MainDb
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.sharedPref.SharedPref
import java.util.*
import java.util.Locale.ENGLISH
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPref
    private val dataModel: DataModel by viewModels()
    private lateinit var mItemViewModel: ItemViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        sharedPref = SharedPref(this)

        if (sharedPref.getValue() && sharedPref.getValueDB()){
            openFrag(HomePage.newInstance(), R.id.placeHolder)
        }else if (sharedPref.getValue()){
           openFrag(EmptyHomeFragment.newInstance(), R.id.placeHolder)
        }else {
            sharedPref.saveValue(true)
            openFrag(OnboardingFragment.newInstance(), R.id.placeHolder)
        }
        mItemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        sharedPref()
        fragmentClickListener()

    }

    private fun sharedPref() {
        mItemViewModel.readAllData.observe(this) {itemCount ->
            if (itemCount.isEmpty()){
                sharedPref.saveValueDB(false)
                openFrag(EmptyHomeFragment.newInstance(), R.id.placeHolder)
            }
        }
    }


    private fun fragmentClickListener() {
        dataModel.openFragEmptyFragment.observe(this) {
            openFrag(EmptyHomeFragment.newInstance(), R.id.placeHolder)
        }

        dataModel.newTaskFromHomePage.observe(this) {
            openFrag(AddNewTaskFragment.newInstance(), R.id.placeHolder)
        }

        dataModel.backFromAddPage.observe(this) {
            openFrag(HomePage.newInstance(), R.id.placeHolder)
        }
        dataModel.saveAndBackFromAddPage.observe(this) {
            openFrag(HomePage.newInstance(), R.id.placeHolder)
        }
            dataModel.updateFragment.observe(this) {
                openFrag(UpdateFragment.newInstance(), R.id.placeHolder)
            }
    }

    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_right, R.anim.slide_left)
            .replace(idHolder, f)
            .commit()
    }

}