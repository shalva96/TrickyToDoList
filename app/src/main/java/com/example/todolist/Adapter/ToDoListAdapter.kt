package com.example.todolist.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.CheckResult
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DataClass.DataModel
import com.example.todolist.Db.Dao
import com.example.todolist.Db.Item
import com.example.todolist.Db.ItemViewModel
import com.example.todolist.R
import com.example.todolist.databinding.ToDoItemBinding

class ToDoListAdapter(private val listener: Listener?) : RecyclerView.Adapter<ToDoListAdapter.ToDoListHolder>() {

    private var toDoList = emptyList<Item>()
    private var myBoolean: Boolean = false


    inner class ToDoListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ToDoItemBinding.bind(view)
        private var itemForBgChange = ArrayList<Item>()

        @SuppressLint("ResourceAsColor")
        fun setData(item: Item, listener: Listener?) {
            binding.checkboxSample.isChecked = item.checked
            binding.descriptionSample.text = item.text
            binding.dateSample.text = item.date
            if (binding.dateSample.text == " ") binding.dateSample.visibility = View.GONE
            when (item.color) {
                0 -> {
                    binding.smallRedCircle.visibility = View.VISIBLE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                1 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.VISIBLE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                2 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.VISIBLE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                3 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.VISIBLE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                4 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.VISIBLE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                5 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.VISIBLE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                6 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.VISIBLE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                7 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.VISIBLE
                    binding.smallPinkCircle.visibility = View.GONE
                }
                8 -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.VISIBLE
                }
                else -> {
                    binding.smallRedCircle.visibility = View.GONE
                    binding.smallOrangeCircle.visibility = View.GONE
                    binding.smallYellowCircle.visibility = View.GONE
                    binding.smallGreenCircle.visibility = View.GONE
                    binding.smallLightblueCircle.visibility = View.GONE
                    binding.smallBlueCircle.visibility = View.GONE
                    binding.smallPurpleCircle.visibility = View.GONE
                    binding.smallLightpurpleCircle.visibility = View.GONE
                    binding.smallPinkCircle.visibility = View.GONE
                }
            }


            if (myBoolean) {
                binding.toDoBackground.setBackgroundColor(Color.WHITE)
            }

            binding.itemLayout.setOnClickListener {
                listener?.onClick(item)

                if (itemForBgChange.contains(item)) {
                    binding.toDoBackground.setBackgroundColor(Color.WHITE)
                    itemForBgChange.remove(item)
                } else {
                    itemForBgChange.addAll(listOf(item))
                    binding.toDoBackground.setBackgroundColor(R.drawable.to_do_list_bg)
                }



            }
            binding.itemLayout.setOnLongClickListener {
                itemForBgChange.addAll(listOf(item))
                binding.toDoBackground.setBackgroundColor(R.drawable.to_do_list_bg)
                listener?.onLongClick(item)
                true
            }
            binding.checkboxSample.setOnClickListener {
                if ( binding.checkboxSample.isChecked ) {
                    listener?.checkBox(item.id!!, true)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return ToDoListHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ToDoListHolder, position: Int) {
        val currentItem = toDoList[position]
        holder.setData(currentItem, listener)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun addItem(item: List<Item>) {
        this.toDoList.toMutableList().clear()
        this.toDoList = item
        notifyDataSetChanged()
    }

    fun setBoolean(value: Boolean) {
        myBoolean = value
    }

    interface Listener {
        fun onClick(item: Item)
        fun onLongClick(item: Item)
        fun checkBox(id: Int, checked: Boolean)
    }

}