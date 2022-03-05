package com.example.foodfactory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfactory.R
import com.example.foodfactory.model.Category

class MenuAdapter(private val categorylist: ArrayList<Category>) : RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.category_item,
            parent, false)

        return MenuAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category : Category = categorylist[position]
        holder.title.text= category.title.toString()

    }

    override fun getItemCount(): Int {

        return categorylist.size
    }
    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.category)

    }
}