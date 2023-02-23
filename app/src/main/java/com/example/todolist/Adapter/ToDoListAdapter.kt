package com.example.todolist.Adapter

import android.annotation.SuppressLint
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

    inner class ToDoListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ToDoItemBinding.bind(view)

        @SuppressLint("ResourceAsColor")
        fun setData(item: Item, listener: Listener?) {
            binding.checkboxSample.isChecked = item.checked
            binding.descriptionSample.text = item.text
            binding.dateSample.text = item.date
            if (binding.dateSample.text == " ") binding.dateSample.visibility = View.GONE
            when (item.color) {
                0 -> { binding.smallRedCircle.isVisible = true }
                1 -> { binding.smallOrangeCircle.isVisible = true  }
                2 -> { binding.smallYellowCircle.isVisible = true  }
                3 -> { binding.smallGreenCircle.isVisible = true }
                4 -> { binding.smallLightblueCircle.isVisible = true  }
                5 -> { binding.smallBlueCircle.isVisible = true  }
                6 -> { binding.smallPurpleCircle.isVisible = true  }
                7 -> { binding.smallLightpurpleCircle.isVisible = true  }
                8 -> { binding.smallPinkCircle.isVisible = true  }
            }

            binding.itemLayout.setOnClickListener {
                listener?.onClick(item, binding)
            }
            binding.itemLayout.setOnLongClickListener {
                listener?.onLongClick(item)
                true
            }
//            binding.checkboxSample.setOnCheckedChangeListener { _ , isChecked ->
////                item.id?.let { listener?.checkBox(it, isChecked) }
//                listener?.checkBox(item.id!!, isChecked)
//            }
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
            this.toDoList = item
            notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(item: Item, binding: ToDoItemBinding)
        fun onLongClick(item: Item)
        fun checkBox(id: Int, checked: Boolean)
    }
}