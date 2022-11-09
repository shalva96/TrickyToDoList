package com.example.todolist.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DataClass.HomePageData
import com.example.todolist.R

class HomePageAdapter: RecyclerView.Adapter<HomePageAdapter.HomePageHolder>() {

    private val toDoList = ArrayList<HomePageData>()

    inner class HomePageHolder(view: View): RecyclerView.ViewHolder(view) {

//        fun setData(item: HomePageData) {
//            myText.text = item.title
//            Picasso.get().load(item.image).into(img)
//
//            recyclerItem.setOnClickListener {
//                listener?.itemClicked(item)
//            }
//
//        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.to_do_recycler, parent, false)
        return HomePageHolder(view)
    }

    override fun onBindViewHolder(holder: HomePageHolder, position: Int) {
        val currentItem = toDoList[position]
//        holder.setData(currentItem)
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