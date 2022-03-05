package com.example.foodfactory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodfactory.R
import com.example.foodfactory.model.Dish

class subMenuAdapter(private val dishlist : ArrayList<Dish>) : Adapter<subMenuAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(
            R.layout.dish_item,
        parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dish : Dish = dishlist[position]
        holder.ID.text= dish.ID
        holder.Name.text= dish.Name
        holder.price.text= dish.Price.toString()
        holder.available.text= dish.Availability.toString()
        holder.veg.text = dish.Vegetarian.toString()
    }

    override fun getItemCount(): Int {

        return dishlist.size
    }
    public class MyViewHolder(itemView : View) : ViewHolder(itemView)
    {
        val ID : TextView = itemView.findViewById(R.id.id_dish)
        val Name :TextView = itemView.findViewById(R.id.dish_name)
        val price :TextView = itemView.findViewById(R.id.price)
        val available: TextView = itemView.findViewById(R.id.avail)
        val veg : TextView = itemView.findViewById(R.id.veg)
    }
}