package com.example.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Adapter.CompletedListAdapter
import com.example.todolist.Adapter.ToDoListAdapter
import com.example.todolist.Base.BaseFragment
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Dao
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.databinding.FragmentHomePageBinding
import com.example.todolist.databinding.ToDoItemBinding
import com.example.todolist.sharedPref.SharedPref
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomePage : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate), ToDoListAdapter.Listener, CompletedListAdapter.Listener {

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
    private var myIdItems = ArrayList<Int>()
    private var adapterBinding: ToDoItemBinding? = null


    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var completedListAdapter: CompletedListAdapter
    private lateinit var mItemViewModel: ItemViewModel


    override fun start() {
        init()
        calendar()
    }

    override fun onClick() {
        clickListener()

        binding.sortBy.setOnClickListener {
            if (!binding.sortBlock.isVisible) {
                binding.sortBlock.visibility = View.VISIBLE
                binding.sortBackground.isVisible = true
            } else {
                binding.sortBlock.visibility = View.INVISIBLE
                binding.sortBackground.isVisible = false
            }
        }
        binding.radioGroup.setOnCheckedChangeListener { radioBtn, checkedId ->
            when (checkedId) {
                binding.dateAdded.id -> mItemViewModel.sortByDateAdded.observe(viewLifecycleOwner) { item ->
                    toDoListAdapter.addItem(item)
                    binding.sortBlock.visibility = View.INVISIBLE
                    binding.sortBackground.isVisible = false
                    binding.sortBy.text = getString(R.string.dateAdded)
                }
                binding.dueDate.id -> mItemViewModel.sortByDate.observe(viewLifecycleOwner) { item ->
//                    toDoListAdapter.sortByDate(true)
                    toDoListAdapter.addItem(item)
                    binding.sortBlock.visibility = View.INVISIBLE
                    binding.sortBackground.isVisible = false
                    binding.sortBy.text = getString(R.string.dueDate)
                }
                binding.colorLabel.id -> mItemViewModel.sortByColor.observe(viewLifecycleOwner) { item ->
                    toDoListAdapter.addItem(item)
                    binding.sortBlock.visibility = View.INVISIBLE
                    binding.sortBackground.isVisible = false
                    binding.sortBy.text = getString(R.string.colorLabel)
                }
            }
        }
    }


    companion object {
        fun newInstance() = HomePage()
    }

    private fun init() {
        val toDoRecyclerView = binding.todoRecyclerView
        toDoListAdapter = ToDoListAdapter(this)
        toDoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        toDoRecyclerView.adapter = toDoListAdapter

        val completedRecyclerView = binding.completedRecyclerView
        completedListAdapter = CompletedListAdapter(this)
        completedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        completedRecyclerView.adapter = completedListAdapter


        //ItemViewModel
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.unCheck.observe(viewLifecycleOwner) { item ->
            toDoListAdapter.addItem(item)
        }
        mItemViewModel.checked.observe(viewLifecycleOwner) { item ->
            completedListAdapter.addItem(item)
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun clickListener() {

        binding.homeNewTask.setOnClickListener {
            dataModel.newTaskFromHomePage.value = true
        }


        binding.delete.setOnClickListener {
            mItemViewModel.deleteSome(myIdItems)
            toDoListAdapter.setBoolean(true)

            binding.longClickMenu.isVisible = false
        }
        binding.XVector.setOnClickListener {
            binding.longClickMenu.isVisible = false
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun calendar() {

        when (prevMonth) {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                if (firstDay == -1) {
                    firstDay = prevMonthDays - 1
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
                }else if (firstDay <= 0) {
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
                } else {
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
        }
    } // Calendar end!!

    // Interface from ToDoListAdapter
    override fun onClick(item: Item) {
        if (!binding.longClickMenu.isVisible) {
            dataModel.updateFragment.value = true
            dataModel.recyclerViewItemId.value = item
        }

        if (myIdItems.contains(item.id)) {
            myIdItems.remove(item.id)
            if (myIdItems.isEmpty()) {
                binding.longClickMenu.isVisible = false
            }
        } else {
            myIdItems.addAll(listOf(item.id!!))
        }
    }

    override fun onLongClick(item: Item) {
        myIdItems.add(item.id!!)
        dataModel.recyclerViewDeleteItemId.value = item
        binding.longClickMenu.isVisible = true
        binding.fullScreen.setOnClickListener {
            binding.longClickMenu.isVisible = false
        }
        binding.done.setOnClickListener {
            mItemViewModel.updateCheckboxForItem(itemId = item.id!!, true)
            binding.longClickMenu.isVisible = false
        }

        adapterBinding?.toDoBackground?.setOnClickListener {
            dataModel.recyclerViewDeleteItemId.value = item
        }

    }

    override fun checkBox(id: Int, checked: Boolean) {
        mItemViewModel.updateCheckboxForItem(id, checked)
    }


    override fun checkBoxCompleted(id: Int, checked: Boolean) {
        mItemViewModel.updateCheckboxForItem(id, checked)
    }

}