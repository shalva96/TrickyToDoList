package com.example.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Adapter.HomePageAdapter
import com.example.todolist.DataClass.HomePageData
import com.example.todolist.databinding.FragmentHomePageBinding

class HomePage : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomePageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomePageBinding.inflate(inflater)
        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()



        binding.tuesday.setOnClickListener {
            binding.tuesday.setBackgroundResource(R.drawable.ic_rectangle)
            binding.monday.setBackgroundResource(R.drawable.ic_rectangle_white)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomePage()
    }


    private fun init() {
//        adapter = HomePageAdapter()
//        binding.apply {
//            todoRecyclerView.layoutManager = LinearLayoutManager(this)
//            todoRecyclerView.adapter = adapter
//            val list = getItems()
//            adapter.addItem(list)
//        }
    }

    private fun getItems(): ArrayList<HomePageData> {
        val homePageDataList = ArrayList<HomePageData>()
        homePageDataList.add(HomePageData(
            false, "13 sundae alcohol day", "333333", "Due 25 Aug."
        ))
        homePageDataList.add(HomePageData(
            false, "SKTT1 Lose championship", "542f6ew", "Due 06 Oct."
        ))
        homePageDataList.addAll(homePageDataList)
        homePageDataList.addAll(homePageDataList)
        return homePageDataList
    }


}