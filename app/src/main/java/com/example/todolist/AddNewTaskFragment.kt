package com.example.todolist

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Item
import com.example.todolist.Db.MainDb
import com.example.todolist.databinding.FragmentAddNewTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddNewTaskFragment : Fragment(), DataSelected{

    private var _binding: FragmentAddNewTaskBinding? = null
    private val binding get() = _binding!!
    private val dataModel: DataModel by activityViewModels()
    // Class for chose date
    class DatePickerFragment(private val dateSelected: DataSelected): DialogFragment(),  DatePickerDialog.OnDateSetListener {
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

        binding.backContainer.setOnClickListener {
            dataModel.backFromAddPage.value = true
        }

        binding.calendarIcon.setOnClickListener {
            showDatePicker()
        }





    }
    private fun oval(){


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
        // add DB
        val db = MainDb.getDb(requireActivity())
        binding.addPageSaveBtn.setOnClickListener {
            val item = Item(null,
                false,
                binding.AddEditText.text.toString(),
                "R.color.firs_color",
                "$viewFormattedDate"
            )
            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().insertItem(item)
            }
        }

    }
}

// Get calendar date
interface  DataSelected {
    fun receiveDate(year: Int,month: Int,dayOfMonth: Int)
}