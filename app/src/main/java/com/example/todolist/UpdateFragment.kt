package com.example.todolist

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.Db.MainDb
import com.example.todolist.databinding.FragmentUpdateBinding
import com.example.todolist.sharedPref.SharedPref
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment(), DataSelected {


    private var _binding: FragmentUpdateBinding? = null
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
        _binding = FragmentUpdateBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataModel.recyclerViewItem.observe(viewLifecycleOwner) {
            binding.updateAddEditText.setText(it)
        }


        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        sharedPref = SharedPref(requireContext())

        binding.updateBackContainer.setOnClickListener {
            dataModel.backFromAddPage.value = true
        }

        binding.updateCalendarIcon.setOnClickListener {
            showDatePicker()
        }
        // add DB
        val db = MainDb.getDb(requireContext())
        binding.updateAddPageSaveBtn.setOnClickListener {
            insertDataToDatabase()
        }
        binding.updateAddPageCancelBtn.setOnClickListener {
            dataModel.saveAndBackFromAddPage.value = true
        }
    }

    private fun insertDataToDatabase() {

        val description = binding.updateAddEditText.text.toString()

        if (inputCheck(description)) {
            //Create item object
            val item = Item(null, false, binding.updateAddEditText.text.toString(), color, "$viewFormatDate")
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
        val datePickerFragment = AddNewTaskFragment.DatePickerFragment(this)
        datePickerFragment.show(parentFragmentManager, "datePicker")
    }




    companion object {
        fun newInstance() = UpdateFragment()
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
}
