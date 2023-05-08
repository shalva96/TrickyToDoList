package com.example.todolist

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.Base.BaseFragment
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.databinding.FragmentAddNewTaskBinding
import com.example.todolist.sharedPref.SharedPref
import java.text.SimpleDateFormat
import java.util.*


class AddNewTaskFragment :
    BaseFragment<FragmentAddNewTaskBinding>(FragmentAddNewTaskBinding::inflate), DataSelected {

    private val dataModel: DataModel by activityViewModels()
    private lateinit var sharedPref: SharedPref
    private lateinit var mItemViewModel: ItemViewModel
    var color: Int = 9
    private var viewFormatDate: String = "33"
    private var viewForSortByDate: String = "Z"

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
            Log.d("MyTag", "Got the date")
        }
    }


    override fun start() {
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        sharedPref = SharedPref(requireContext())
    }

    override fun onClick() {
        clickListener()
    }

    fun onClick(item: Item) {
        dataModel.newTaskFromHomePage.value = true
        Toast.makeText(requireActivity(), "Clicked on: ${item.id}", Toast.LENGTH_LONG).show()
    }


    private fun insertDataToDatabase() {

        val description = binding.AddEditText.text.toString()

        if (inputCheck(description)) {
            //Create item object
            val item = Item(null, false, description, color, viewForSortByDate, "$viewFormatDate")
            //Add Data to DB
            mItemViewModel.addItem(item)

            // Set on boarding and back home page
            sharedPref.saveValueDB(true)
            dataModel.saveAndBackFromAddPage.value = true
        } else {
            Toast.makeText(requireContext(), "Please fill out description", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(description: String): Boolean {
        return !(TextUtils.isEmpty(description))
    }


    private fun showDatePicker() {
        val datePickerFragment = DatePickerFragment(this)
        datePickerFragment.show(parentFragmentManager, "datePicker")
    }


    companion object {
        fun newInstance() = AddNewTaskFragment()
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
            binding.choseDate.visibility = View.VISIBLE
            binding.selectedDate.text = "Due $viewFormattedDate"
            binding.calendarIcon.visibility = View.GONE
        }
        binding.cleanDate.setOnClickListener {
            val day = SimpleDateFormat("dd MMM yyyy")
            val date: String = day.format(Date())
            binding.choseDate.visibility = View.GONE
            viewFormattedDate = date.format(Date())
            binding.selectedDate.text = "Due $viewFormattedDate"
            binding.calendarIcon.visibility = View.VISIBLE
        }
        viewFormatDate = viewFormattedDate

        if (viewFormatDate.contains("Jan") || viewFormatDate.contains("янв.")) {
            viewForSortByDate = "A$viewFormatDate"
        }else if (viewFormatDate.contains("Feb") || viewFormatDate.contains("февр.")) {
            viewForSortByDate = "B$viewFormatDate"
        }else if (viewFormatDate.contains("Mar") || viewFormatDate.contains("мар.")) {
            viewForSortByDate = "C$viewFormatDate"
        }else if (viewFormatDate.contains("Apr") || viewFormatDate.contains("апрю")) {
            viewForSortByDate = "D$viewFormatDate"
        }else if (viewFormatDate.contains("May") || viewFormatDate.contains("мая")) {
            viewForSortByDate = "E$viewFormatDate"
        }else if (viewFormatDate.contains("Jun") || viewFormatDate.contains("июн.")) {
            viewForSortByDate = "F$viewFormatDate"
        }else if (viewFormatDate.contains("Jul") || viewFormatDate.contains("июл.")) {
            viewForSortByDate = "G$viewFormatDate"
        }else if (viewFormatDate.contains("Aug") || viewFormatDate.contains("авг.")) {
            viewForSortByDate = "H$viewFormatDate"
        }else if (viewFormatDate.contains("Sep") || viewFormatDate.contains("сент.")) {
            viewForSortByDate = "I$viewFormatDate"
        }else if (viewFormatDate.contains("Oct") || viewFormatDate.contains("окт.")) {
            viewForSortByDate = "J0$viewFormatDate"
        }else if (viewFormatDate.contains("Nov") || viewFormatDate.contains("нояб.")) {
            viewForSortByDate = "K1$viewFormatDate"
        }else if (viewFormatDate.contains("Dec") || viewFormatDate.contains("дек.")) {
            viewForSortByDate = "L2$viewFormatDate"
        }else {
            viewForSortByDate = "Z"
        }



    }

    private fun clickListener() {
        binding.backContainer.setOnClickListener {
            dataModel.backFromAddPage.value = true
        }

        binding.calendarIcon.setOnClickListener {
            showDatePicker()
        }


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

        // add DB
        binding.addPageSaveBtn.setOnClickListener {
            insertDataToDatabase()
        }
        binding.addPageCancelBtn.setOnClickListener {
            dataModel.saveAndBackFromAddPage.value = true
        }
    }

}


// Get calendar date
interface DataSelected {
    fun receiveDate(year: Int, month: Int, dayOfMonth: Int)
}