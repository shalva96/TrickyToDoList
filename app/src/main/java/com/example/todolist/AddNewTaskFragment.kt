package com.example.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolist.databinding.FragmentAddNewTaskBinding


class AddNewTaskFragment : Fragment() {

    private var _binding: FragmentAddNewTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNewTaskBinding.inflate(inflater)
        return binding.root

    }

    companion object {
        fun newInstance() = AddNewTaskFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}