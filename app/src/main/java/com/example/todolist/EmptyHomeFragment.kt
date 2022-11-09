package com.example.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolist.databinding.FragmentEmptyHomeBinding
import com.example.todolist.databinding.FragmentHomePageBinding

class EmptyHomeFragment : Fragment() {

    private var _binding: FragmentEmptyHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEmptyHomeBinding.inflate(inflater)
        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {

        @JvmStatic
        fun newInstance() = EmptyHomeFragment()
    }


}