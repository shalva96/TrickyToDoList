package com.example.todolist

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Adapter.CompletedListAdapter
import com.example.todolist.Adapter.ToDoListAdapter
import com.example.todolist.Base.BaseFragment
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.databinding.FragmentHomePageBinding
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


    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var completedListAdapter: CompletedListAdapter
    private lateinit var mItemViewModel: ItemViewModel


    override fun start() {
        init()
        calendar()
    }

    override fun onClick() {
        binding.homeNewTask.setOnClickListener {
            dataModel.newTaskFromHomePage.value = true
        }

        binding.delete.setOnClickListener {
            mItemViewModel.deleteSome(myIdItems)
            toDoListAdapter.setBoolean(true)
            binding.longClickMenu.isVisible = false
            val animationSlideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            binding.longClickMenu.startAnimation(animationSlideDown)
        }
        binding.done.setOnClickListener {
            mItemViewModel.updateCheckboxBtnDone(myIdItems)
            binding.longClickMenu.isVisible = false
            val animationSlideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            binding.longClickMenu.startAnimation(animationSlideDown)
            toDoListAdapter.setBoolean(true)
        }
        binding.XVector.setOnClickListener {
            binding.longClickMenu.isVisible = false
            toDoListAdapter.setBoolean(true)
            val animationSlideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            binding.longClickMenu.startAnimation(animationSlideDown)
        }

        binding.sortBy.setOnClickListener {
            if (!binding.sortBlock.isVisible) {
                binding.sortBlock.visibility = View.VISIBLE
                binding.sortBackground.isVisible = true
                val bgAnimIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                val sortAnimIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
                binding.sortBlock.startAnimation(sortAnimIn)
                binding.sortBackground.startAnimation(bgAnimIn)
            }
        }
        binding.sortBackground.setOnClickListener {
            binding.sortBlock.visibility = View.INVISIBLE
            binding.sortBackground.isVisible = false
            val bgAnimOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
            val sortAnimOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            binding.sortBlock.startAnimation(sortAnimOut)
            binding.sortBackground.startAnimation(bgAnimOut)
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
            val bgAnimOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
            val sortAnimOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
            binding.sortBlock.startAnimation(sortAnimOut)
            binding.sortBackground.startAnimation(bgAnimOut)
        }

    }


    companion object {
        fun newInstance() = HomePage()
    }

    private fun init() {
        val adapterAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right)
        val toDoRecyclerView = binding.todoRecyclerView
        toDoListAdapter = ToDoListAdapter(this)
        toDoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        toDoRecyclerView.adapter = toDoListAdapter
        binding.todoRecyclerView.startAnimation(adapterAnim)

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
                val animationSlideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
                binding.longClickMenu.startAnimation(animationSlideDown)
            }
        } else {
            myIdItems.addAll(listOf(item.id!!))
        }
    }

    override fun onLongClick(item: Item) {
        myIdItems.add(item.id!!)
        dataModel.recyclerViewDeleteItemId.value = item
        binding.longClickMenu.isVisible = true
        val animationSlideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        binding.longClickMenu.startAnimation(animationSlideUp)
        binding.fullScreen.setOnClickListener {
            binding.longClickMenu.isVisible = false
        }
//        binding.done.setOnClickListener {
//            mItemViewModel.updateCheckboxForItem(itemId = item.id!!, true)
//            binding.longClickMenu.isVisible = false
//            val animationSlideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
//            binding.longClickMenu.startAnimation(animationSlideDown)
//            toDoListAdapter.setBoolean(true)
//        }
    }

    override fun checkBox(id: Int, checked: Boolean) {
        mItemViewModel.updateCheckboxForItem(id, checked)
    }


    override fun checkBoxCompleted(id: Int, checked: Boolean) {
        mItemViewModel.updateCheckboxForItem(id, checked)
    }

}