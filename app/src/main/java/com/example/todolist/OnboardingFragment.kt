package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todolist.Base.BaseFragment
import com.example.todolist.DataClass.DataModel
import com.example.todolist.databinding.FragmentEmptyHomeBinding
import com.example.todolist.databinding.FragmentOnboardingBinding
import com.example.todolist.sharedPref.SharedPref


class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private val dataModel: DataModel by activityViewModels()
    private lateinit var sharedPref: SharedPref


    companion object {
        fun newInstance() = OnboardingFragment()
    }

    override fun start() {
        sharedPref = SharedPref(requireContext())
    }

    override fun onClick() {
        binding.onboardingBtn.setOnClickListener {
            sharedPref.saveValue(true)
            dataModel.openFragEmptyFragment.value = true
        }
    }


}