package com.example.todolist.Base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

typealias inflater<T> = (LayoutInflater, ViewGroup, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(private var inflate: inflater<VB>): Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container!!, false)
        start()
        onClick()

        return _binding!!.root

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    abstract fun start()
    abstract fun onClick()

}