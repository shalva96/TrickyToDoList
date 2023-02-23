package com.example.todolist.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Db.Item
import com.example.todolist.R
import com.example.todolist.databinding.CompletedItemBinding

class CompletedListAdapter(private val listener: Listener?) : RecyclerView.Adapter<CompletedListAdapter.CompletedListHolder>() {

    private var toDoList = emptyList<Item>()

    inner class CompletedListHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CompletedItemBinding.bind(view)

        fun setData(item: Item, listener: Listener?) {
            binding.checkboxSample.isChecked = item.checked
            binding.descriptionSample.text = item.text


//            binding.checkboxSample.setOnCheckedChangeListener { _ , isChecked ->
////                item.id?.let { listener?.checkBoxCompleted(it, isChecked) }
//                listener?.checkBoxCompleted(item.id!!, isChecked)
//            }

            binding.checkboxSample.setOnClickListener {
                if ( !binding.checkboxSample.isChecked ) {
                    listener?.checkBoxCompleted(item.id!!, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.completed_item, parent, false)
        return CompletedListHolder(view)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CompletedListHolder, position: Int) {
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
        fun checkBoxCompleted(id: Int, checked: Boolean)
    }

}