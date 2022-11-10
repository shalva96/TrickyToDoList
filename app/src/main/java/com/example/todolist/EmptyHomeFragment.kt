package com.example.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.FragmentEmptyHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class EmptyHomeFragment : Fragment() {

    private var _binding: FragmentEmptyHomeBinding? = null
    private val binding get() = _binding!!


    private val monthAndYear = SimpleDateFormat("MMM yyyy")
    private val topCalendar: String = monthAndYear.format(Date())

    private val day = SimpleDateFormat("d")
    private val date: String = day.format(Date())
    private var nextDay = date.toInt()
    private val weekDay = SimpleDateFormat("EEEE")
    private val week: String = weekDay.format(Date())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEmptyHomeBinding.inflate(inflater)
        return binding.root

    }


    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarDate.text = topCalendar.format(Date())

        binding.apply {
            if (week.format(Date()) == "Monday" || week.format(Date()) == "понедельник") {
                firstDay.text = "M"
                monday.setBackgroundResource(R.drawable.ic_rectangle)
                firstDay.setTextColor(R.color.custom_color_active_day)
                dayOne.text = date.format(Date())
                dayTwo.text = (++nextDay).toString()
                dayThree.text = (nextDay + 2).toString()
                dayFour.text = (nextDay + 3).toString()
                dayFive.text = (nextDay + 4).toString()
                daySix.text = (nextDay + 5).toString()
                daySeven.text = (nextDay + 6).toString()
            } else if (week.format(Date()) == "Tuesday" || week.format(Date()) == "вторник") {
                secondDay.text = "T"
                secondDay.setTextColor(R.color.custom_color_active_day)
                tuesday.setBackgroundResource(R.drawable.ic_rectangle)
                dayOne.text = (nextDay--).toString()
                dayTwo.text = date.format(Date())
                dayThree.text = (nextDay + 2).toString()
                dayFour.text = (nextDay + 3).toString()
                dayFive.text = (nextDay + 4).toString()
                daySix.text = (nextDay + 5).toString()
                daySeven.text = (nextDay + 6).toString()
            } else if (week.format(Date()) == "Wednesday" || week.format(Date()) == "среда") {
                threeDay.text = "W"
                threeDay.setTextColor(R.color.custom_color_active_day)
                wednesday.setBackgroundResource(R.drawable.ic_rectangle)
                dayOne.text = (nextDay - 2).toString()
                dayTwo.text = (nextDay--).toString()
                dayThree.text = date.format(Date())
                dayFour.text = (nextDay + 2).toString()
                dayFive.text = (nextDay + 3).toString()
                daySix.text = (nextDay + 4).toString()
                daySeven.text = (nextDay + 5).toString()
            } else if (week.format(Date()) == "Thursday" || week.format(Date()) == "четверг") {
                fourDay.text = "T"
                fourDay.setTextColor(R.color.custom_color_active_day)
                thursday.setBackgroundResource(R.drawable.ic_rectangle)
                fourDay.setTextColor(R.color.custom_color_active_day)
                dayOne.text = (nextDay - 3).toString()
                dayTwo.text = (nextDay - 2).toString()
                dayThree.text = (--nextDay).toString()
                dayFour.text = date.format(Date())
                dayFive.text = (nextDay + 2).toString()
                daySix.text = (nextDay + 3).toString()
                daySeven.text = (nextDay + 4).toString()
            } else if (week.format(Date()) == "Friday" || week.format(Date()) == "пятница") {
                fiveDay.text = "F"
                fiveDay.setTextColor(R.color.custom_color_active_day)
                friday.setBackgroundResource(R.drawable.ic_rectangle)
                dayOne.text = (nextDay - 4).toString()
                dayTwo.text = (nextDay - 3).toString()
                dayThree.text = (nextDay - 2).toString()
                dayFour.text = (nextDay--).toString()
                dayFive.text = date.format(Date())
                daySix.text = (nextDay + 2).toString()
                daySeven.text = (nextDay + 3).toString()
            } else if (week.format(Date()) == "Saturday" || week.format(Date()) == "суббота") {
                sixDay.text = "S"
                sixDay.setTextColor(R.color.custom_color_active_day)
                saturday.setBackgroundResource(R.drawable.ic_rectangle)
                dayOne.text = (nextDay - 5).toString()
                dayTwo.text = (nextDay - 4).toString()
                dayThree.text = (nextDay - 3).toString()
                dayFour.text = (nextDay - 2).toString()
                dayFive.text = (nextDay--).toString()
                daySix.text = date.format(Date())
                daySeven.text = (nextDay + 2).toString()
            } else if (week.format(Date()) == "Sunday" || week.format(Date()) == "воскресенье") {
                sevenDay.text = "S"
                sevenDay.setTextColor(R.color.custom_color_active_day)
                sunday.setBackgroundResource(R.drawable.ic_rectangle)
                dayOne.text = (nextDay - 6).toString()
                dayTwo.text = (nextDay - 5).toString()
                dayThree.text = (nextDay - 4).toString()
                dayFour.text = (nextDay - 3).toString()
                dayFive.text = (nextDay - 2).toString()
                daySix.text = (nextDay--).toString()
                daySeven.text = date.format(Date())
            }
        }

        Log.d("TAG", week.format(Date()))
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