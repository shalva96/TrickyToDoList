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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.Db.MainDb
import com.example.todolist.databinding.FragmentAddNewTaskBinding
import com.example.todolist.sharedPref.SharedPref
import java.text.SimpleDateFormat
import java.util.*


class AddNewTaskFragment : Fragment(), DataSelected {

    private var _binding: FragmentAddNewTaskBinding? = null
    private val binding get() = _binding!!
    private val dataModel: DataModel by activityViewModels()
    private lateinit var sharedPref: SharedPref
    private lateinit var mItemViewModel: ItemViewModel
    var color: Int = 0
    var viewFormatDate: String = " "

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNewTaskBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        sharedPref = SharedPref(requireContext())



        binding.backContainer.setOnClickListener {
            dataModel.backFromAddPage.value = true
        }

        binding.calendarIcon.setOnClickListener {
            showDatePicker()
        }

        binding.firsCircle.setOnClickListener {
            color = R.color.firs_color
        }
        binding.secondCircle.setOnClickListener {
            color = R.color.second_color
        }
        binding.threeCircle.setOnClickListener {
            color = R.color.three_color
        }
        binding.fourCircle.setOnClickListener {
            color = R.color.four_color
        }
        binding.fiveCircle.setOnClickListener {
            color = R.color.five_color
        }
        binding.sixCircle.setOnClickListener {
            color = R.color.six_color
        }
        binding.sevenCircle.setOnClickListener {
            color = R.color.seven_color
        }
        binding.eightCircle.setOnClickListener {
            color = R.color.eight_color
        }
        binding.nineCircle.setOnClickListener {
            color = R.color.nine_color
        }

        // add DB
        val db = MainDb.getDb(requireContext())
        binding.addPageSaveBtn.setOnClickListener {
            insertDataToDatabase()
//            val item = Item(null,
//                false,
//                binding.AddEditText.text.toString(),
//                color,
//                "$viewFormatDate"
//            )
//            Log.d("MyTag", "$color")
//            CoroutineScope(Dispatchers.IO).launch {
//                db.getDao().insertItem(item)
//            }
//            sharedPref.saveValueDB(true)
//            dataModel.saveAndBackFromAddPage.value = true
        }
        binding.addPageCancelBtn.setOnClickListener {
            dataModel.saveAndBackFromAddPage.value = true
        }


    }

    private fun insertDataToDatabase() {

        val description = binding.AddEditText.text.toString()

        if (inputCheck(description)) {
            //Create item object
            val item = Item(null, false, binding.AddEditText.text.toString(), color, "$viewFormatDate")
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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


    }
}


// Get calendar date
interface DataSelected {
    fun receiveDate(year: Int, month: Int, dayOfMonth: Int)
}