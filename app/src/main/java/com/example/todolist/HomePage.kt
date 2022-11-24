package com.example.todolist

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Adapter.HomePageAdapter
import com.example.todolist.DataClass.DataModel
import com.example.todolist.DataClass.HomePageData
import com.example.todolist.Db.MainDb
import com.example.todolist.databinding.FragmentHomePageBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomePage : Fragment() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private val dataModel: DataModel by activityViewModels()

    //Calendar
    private val monthAndYear = SimpleDateFormat("MMM yyyy")
    private val topCalendar: String = monthAndYear.format(Date()) // Current Month and Year
    private val day = SimpleDateFormat("d")
    private val date: String = day.format(Date()) // Current Day and Date
    private val calendar: Calendar = Calendar.getInstance()
    private val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) // Live month days
    private val weekDays = calendar.get(7) // Week days
    private var prevMonth = calendar.get(Calendar.MONTH)//Prev month
    private var prevMonthDays = 0


    private lateinit var adapter: HomePageAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomePageBinding.inflate(inflater)
        return binding.root




    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()



        when(prevMonth){
            0 -> prevMonthDays = 31
            1 -> prevMonthDays = 31
            2 -> {
                if (calendar.get(1) % 4 == 0 || calendar.get(1) % 100 == 0 && calendar.get(1) % 400 == 0) {
                    prevMonthDays = 29
                } else {
                    prevMonthDays = 28
                }
            } // MARCH End
            3 -> prevMonthDays = 31
            4 -> prevMonthDays = 30
            5 -> prevMonthDays = 31
            6 -> prevMonthDays = 30
            7 -> prevMonthDays = 31
            8 -> prevMonthDays = 31
            9 -> prevMonthDays = 30
            10 -> prevMonthDays = 31
            11 -> prevMonthDays = 30

        }

        binding.calendarDate.text = topCalendar

        binding.apply {
            if (weekDays == 2) {
                firstDay.text = "M"
                monday.setBackgroundResource(R.drawable.ic_rectangle)
                firstDay.setTextColor(R.color.custom_color_active_day)
                var firstDay = date.format(Date()).toInt()
                if (firstDay <= 0) {
                    firstDay = prevMonthDays
                }
                dayOne.text = firstDay.toString()
                if (firstDay == daysInMonth ) firstDay = 0
                dayTwo.text = (++firstDay).toString()
                if (firstDay == daysInMonth ) firstDay = 0
                dayThree.text = (++firstDay).toString()
                if (firstDay == daysInMonth ) firstDay = 0
                dayFour.text = (++firstDay).toString()
                if (firstDay == daysInMonth) firstDay = 0
                dayFive.text = (++firstDay).toString()
                if (firstDay == daysInMonth) firstDay = 0
                daySix.text = (++firstDay).toString()
                if (firstDay == daysInMonth) firstDay = 0
                daySeven.text = (++firstDay).toString()
            } else if (weekDays == 3) {
                secondDay.text = "T"
                secondDay.setTextColor(R.color.custom_color_active_day)
                tuesday.setBackgroundResource(R.drawable.ic_rectangle)
                var firstDay = date.format(Date()).toInt() - 1
                if (firstDay <= 0) {
                    firstDay = prevMonthDays
                }
                dayOne.text = firstDay.toString()
                if (firstDay == daysInMonth || firstDay == prevMonthDays) firstDay = 0
                dayTwo.text = (++firstDay).toString()
                if (firstDay == daysInMonth || firstDay == prevMonthDays) firstDay = 0
                dayThree.text = (++firstDay).toString()
                if (firstDay == daysInMonth || firstDay == prevMonthDays) firstDay = 0
                dayFour.text = (++firstDay).toString()
                if (firstDay == daysInMonth || firstDay == prevMonthDays) firstDay = 0
                dayFive.text = (++firstDay).toString()
                if (firstDay == daysInMonth || firstDay == prevMonthDays) firstDay = 0
                daySix.text = (++firstDay).toString()
                if (firstDay == daysInMonth || firstDay == prevMonthDays) firstDay = 0
                daySeven.text = (++firstDay).toString()
            } else if (weekDays == 4) {
                threeDay.text = "W"
                threeDay.setTextColor(R.color.custom_color_active_day)
                wednesday.setBackgroundResource(R.drawable.ic_rectangle)
                var firstDay = date.format(Date()).toInt() - 2
                if (firstDay <= 0) {
                    firstDay = prevMonthDays - 1
                    dayOne.text = firstDay.toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }else {
                    dayOne.text = firstDay.toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }

            } else if (weekDays == 5) {
                fourDay.text = "T"
                fourDay.setTextColor(R.color.custom_color_active_day)
                thursday.setBackgroundResource(R.drawable.ic_rectangle)
                fourDay.setTextColor(R.color.custom_color_active_day)
                var firstDay = date.format(Date()).toInt() - 3
                if (firstDay <= 0) {
                    firstDay = prevMonthDays - 2
                    dayOne.text = firstDay.toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }else {
                    dayOne.text = firstDay.toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }
            } else if (weekDays == 6) {
                fiveDay.text = "F"
                fiveDay.setTextColor(R.color.custom_color_active_day)
                friday.setBackgroundResource(R.drawable.ic_rectangle)
                var firstDay = date.format(Date()).toInt() - 4
                if (firstDay <= 0) {
                    firstDay = prevMonthDays - 3
                    dayOne.text = firstDay.toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }else {
                    dayOne.text = firstDay.toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }
            } else if (weekDays == 7) {
                sixDay.text = "S"
                sixDay.setTextColor(R.color.custom_color_active_day)
                saturday.setBackgroundResource(R.drawable.ic_rectangle)
                var firstDay = date.format(Date()).toInt() - 5
                if (firstDay <= 0) {
                    firstDay = prevMonthDays - 4
                    dayOne.text = firstDay.toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }else {
                    dayOne.text = firstDay.toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }
            } else if (weekDays == 1) {
                sevenDay.text = "S"
                sevenDay.setTextColor(R.color.custom_color_active_day)
                sunday.setBackgroundResource(R.drawable.ic_rectangle)
                var firstDay = date.format(Date()).toInt() - 6
                if (firstDay <= 0) {
                    firstDay = prevMonthDays - 5
                    dayOne.text = firstDay.toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == prevMonthDays) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }else {
                    dayOne.text = firstDay.toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayTwo.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayThree.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFour.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    dayFive.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySix.text = (++firstDay).toString()
                    if (firstDay == daysInMonth) firstDay = 0
                    daySeven.text = (++firstDay).toString()
                }
            }
        }// Calendar End!!!


        binding.homeNewTask.setOnClickListener {
            dataModel.newTaskFromHomePage.value = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {
        fun newInstance() = HomePage()
    }


    private fun init() {


            adapter = HomePageAdapter()
            binding.todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.todoRecyclerView.adapter = adapter
            val list = getItems()
            adapter.addItem(list)





    }

    private fun getItems(): ArrayList<HomePageData> {
        val homePageData = ArrayList<HomePageData>()
        val db = MainDb.getDb(requireContext())
        db.getDao().getAllItems().asLiveData().observe(viewLifecycleOwner){ item->
            item.forEach {
                homePageData.addAll(
                    listOf(
                        HomePageData(
                            it.checked,
                            it.text,
                            it.color,
                            it.date
                        )
                    )

                )

            }
        }

        return homePageData
    }
}