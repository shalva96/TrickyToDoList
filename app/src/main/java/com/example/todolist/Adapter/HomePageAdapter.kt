package com.example.todolist.Adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DataClass.HomePageData
import com.example.todolist.R
import com.example.todolist.databinding.ToDoItemBinding

class HomePageAdapter() : RecyclerView.Adapter<HomePageAdapter.HomePageHolder>() {

    private val toDoList = ArrayList<HomePageData>()

    inner class HomePageHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ToDoItemBinding.bind(view)


        fun setData(item: HomePageData) {
            binding.descriptionSample.text = item.description
            binding.dateSample.text = item.date
            if (binding.dateSample.text == " ") binding.dateSample.visibility = View.GONE
            Log.d("MyTag", "${item.color}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return HomePageHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HomePageHolder, position: Int) {
        val currentItem = toDoList[position]
        holder.setData(currentItem)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun addItem(item: ArrayList<HomePageData>) {
        this.toDoList.clear()
        this.toDoList.addAll(item)
        notifyDataSetChanged()
    }


}