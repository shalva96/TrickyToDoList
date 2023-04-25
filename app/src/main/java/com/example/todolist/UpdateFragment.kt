package com.example.todolist

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.Base.BaseFragment
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.databinding.FragmentUpdateBinding
import com.example.todolist.sharedPref.SharedPref
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment() : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate), DataSelected {

    private val dataModel: DataModel by activityViewModels()
    private lateinit var sharedPref: SharedPref
    private lateinit var mItemViewModel: ItemViewModel
    var color: Int = 9
    var viewFormatDate: String = " "
    var itemId: Int = 0

    // Class for chose date
    class DatePickerFragment(private val dateSelected: DataSelected) : DialogFragment(),
        DatePickerDialog.OnDateSetListener {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireContext(), this, year, month, dayOfMonth)

        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            dateSelected.receiveDate(year, month, dayOfMonth)
        }
    }

    override fun start() {
        init()
        getVariableForUpdate()
    }

    override fun onClick() {
        clickListener()
    }



    private fun init() {
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        sharedPref = SharedPref(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getVariableForUpdate() {
        dataModel.recyclerViewItemId.observe(viewLifecycleOwner) {
            this.itemId = it.id!!
            binding.updateAddEditText.setText(it.text)
            if (it.date != " ") {
                viewFormatDate = it.date
                color = it.color
                when(it.color) {
                    0 -> binding.firsOval.isVisible = true
                    1 -> binding.secondOval.isVisible = true
                    2 -> binding.threeOval.isVisible = true
                    3 -> binding.fourOval.isVisible = true
                    4 -> binding.fiveOval.isVisible = true
                    5 -> binding.sixOval.isVisible = true
                    6 -> binding.sevenOval.isVisible = true
                    7 -> binding.eightOval.isVisible = true
                    8 -> binding.nineOval.isVisible = true
                }
                binding.updateCalendarIcon.visibility = View.GONE
                binding.updateChoseDate.visibility = View.VISIBLE
                binding.updateSelectedDate.text = it.date
            }
            binding.updateCleanDate.setOnClickListener {
                binding.updateChoseDate.visibility = View.GONE
                binding.updateCalendarIcon.visibility = View.VISIBLE
            }
        }
    }

    private fun clickListener() {
        binding.updateBackContainer.setOnClickListener {
            dataModel.backFromAddPage.value = true
        }
        binding.updateCalendarIcon.setOnClickListener {
            showDatePicker()
        }
        binding.updateAddPageSaveBtn.setOnClickListener {
            updateItem()
        }
        binding.updateAddPageCancelBtn.setOnClickListener {
            dataModel.saveAndBackFromAddPage.value = true
        }
        circleClickListener()
    }

    private fun updateItem() {
        val description = binding.updateAddEditText.text.toString()
        if (inputCheck(description)) {

            val updateItem = Item(itemId, false, description, color, "$viewFormatDate")
            mItemViewModel.updateItem(updateItem)
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Please fill out description", Toast.LENGTH_SHORT)
                .show()
        }
        dataModel.saveAndBackFromAddPage.value = true
    }

    private fun inputCheck(description: String): Boolean {
        return !(TextUtils.isEmpty(description))
    }


    private fun showDatePicker() {
        val datePickerFragment = AddNewTaskFragment.DatePickerFragment(this)
        datePickerFragment.show(parentFragmentManager, "datePicker")
    }


    companion object {
        fun newInstance() = UpdateFragment()
    }


    // This is function that will be invoked in our fragment when a user picks a date
    @RequiresApi(Build.VERSION_CODES.N)
    override fun receiveDate(year: Int, month: Int, dayOfMonth: Int) {

        val calendar = GregorianCalendar()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)


        val viewFormatter = SimpleDateFormat("dd MMM")
        var viewFormattedDate: String = viewFormatter.format(calendar.time)
        if (Calendar.DAY_OF_MONTH != dayOfMonth && Calendar.MONTH != month && Calendar.YEAR != year) {
            binding.updateChoseDate.visibility = View.VISIBLE
            binding.updateSelectedDate.text = "Due $viewFormattedDate"
            binding.updateCalendarIcon.visibility = View.GONE
        }
        binding.updateCleanDate.setOnClickListener {
            val day = SimpleDateFormat("dd MMM yyyy")
            val date: String = day.format(Date())
            binding.updateChoseDate.visibility = View.GONE
            viewFormattedDate = date.format(Date())
            binding.updateSelectedDate.text = "Due $viewFormattedDate"
            binding.updateCalendarIcon.visibility = View.VISIBLE
        }
        viewFormatDate = viewFormattedDate

    }


    private fun circleClickListener() {
        binding.firstInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.VISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 0
        }
        binding.secondInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.VISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 1
        }
        binding.threeInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.VISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 2
        }
        binding.fourInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.VISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 3
        }
        binding.fiveInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.VISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 4
        }
        binding.sixInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.VISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 5
        }
        binding.sevenInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.VISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 6
        }
        binding.eightInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.VISIBLE
            binding.nineOval.visibility = View.INVISIBLE
            color = 7
        }
        binding.nineInnerCircle.setOnClickListener {
            binding.firsOval.visibility = View.INVISIBLE
            binding.secondOval.visibility = View.INVISIBLE
            binding.threeOval.visibility = View.INVISIBLE
            binding.fourOval.visibility = View.INVISIBLE
            binding.fiveOval.visibility = View.INVISIBLE
            binding.sixOval.visibility = View.INVISIBLE
            binding.sevenOval.visibility = View.INVISIBLE
            binding.eightOval.visibility = View.INVISIBLE
            binding.nineOval.visibility = View.VISIBLE
            color = 8
        }
    }



}
