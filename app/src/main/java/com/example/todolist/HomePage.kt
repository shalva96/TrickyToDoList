package com.example.todolist

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Adapter.CompletedListAdapter
import com.example.todolist.Adapter.ToDoListAdapter
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Dao
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.databinding.FragmentHomePageBinding
import com.example.todolist.databinding.ToDoItemBinding
import com.example.todolist.sharedPref.SharedPref
import java.text.SimpleDateFormat
import java.util.*


class HomePage : Fragment(), ToDoListAdapter.Listener, CompletedListAdapter.Listener {

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
    private var idForDeleteItem: Item? = null
    private var adapterBinding: ToDoItemBinding? = null
    private var myIdItems = listOf<Int>()



    private lateinit var sharedPref: SharedPref
    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var completedListAdapter: CompletedListAdapter
    private lateinit var mItemViewModel: ItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root


    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        calendar()
        clickListener()

//        if (binding.longClickMenu.isVisible) {
//
//        }else {
//            adapterBinding?.toDoBackground?.setBackgroundResource(R.color.white)
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HomePage()
    }

    private fun init() {
        val toDoRecyclerView = binding.todoRecyclerView
        toDoListAdapter = ToDoListAdapter(this)
        toDoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        toDoRecyclerView.adapter = toDoListAdapter
//sad
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
                    binding.sortBy.text = "Date added"
                }
                binding.dueDate.id -> mItemViewModel.sortByDate.observe(viewLifecycleOwner) { item ->
                    toDoListAdapter.addItem(item)
                    binding.sortBlock.visibility = View.INVISIBLE
                    binding.sortBackground.isVisible = false
                    binding.sortBy.text = "Due date"
                }
                binding.colorLabel.id -> mItemViewModel.sortByColor.observe(viewLifecycleOwner) { item ->
                    toDoListAdapter.addItem(item)
                    binding.sortBlock.visibility = View.INVISIBLE
                    binding.sortBackground.isVisible = false
                    binding.sortBy.text = "Color label"
                }
            }
        }

        binding.homeNewTask.setOnClickListener {
            dataModel.newTaskFromHomePage.value = true
        }

        binding.delete.setOnClickListener {
            dataModel.recyclerViewDeleteItemId.observe(viewLifecycleOwner) {
                idForDeleteItem = it
            }
            idForDeleteItem?.let { item -> mItemViewModel.delete(item) }

            myIdItems.forEach { item ->
                mItemViewModel.deleteSome(listOf(item))
            }
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

    // Interface from HomePageAdapter
    override fun onClick(item: Item) {
        if (!binding.longClickMenu.isVisible) {
            dataModel.updateFragment.value = true
            dataModel.recyclerViewItemId.value = item
        }

        myIdItems = myIdItems + listOf(item.id) as List<Int>
    }

    override fun onLongClick(item: Item) {
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
        Log.d("MyTag", "$id")
    }

    override fun checkBoxCompleted(id: Int, checked: Boolean) {
        mItemViewModel.updateCheckboxForItem(id, checked)
    }
}