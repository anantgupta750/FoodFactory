package com.example.foodfactory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodfactory.R
import com.example.foodfactory.model.OrderView
import com.example.foodfactory.ui.OrderFragment

class OrderAdapter(
    val orderList: ArrayList<OrderView>,
    private val orderClickListener: OrderFragment
) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.order_item, parent, false
        )
        return OrderAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
        holder.btnDel.setOnClickListener {
            orderClickListener.onCellClickListener(it,holder)
        }

    }

    override fun getItemCount() = orderList.size

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgOrderSubCategory)
        val dish_name: TextView = itemView.findViewById(R.id.textOrderSubCategory)
        val price:TextView = itemView.findViewById(R.id.priceOrderDish)
        val qty: TextView = itemView.findViewById(R.id.txtOrderQty)
        val veg: ImageView = itemView.findViewById(R.id.vegOrder)
        val btnDel: ImageView = itemView.findViewById(R.id.deleteOrder)

        fun bind(order: OrderView) {
            dish_name.text = order.name
            qty.text = order.qty.toString()
            price.text = order.price.toString()
            btnDel.tag = order
            Glide.with(image)
                .load(order.image)
                .placeholder(R.drawable.chef)
                .into(image)
            if (order.veg){
                Glide.with(veg)
                    .load(R.drawable.veg)
                    .placeholder(R.drawable.veg)
                    .into(veg)
            }
            else {
                Glide.with(veg)
                    .load(R.drawable.nonveg)
                    .placeholder(R.drawable.nonveg)
                    .into(veg)
            }
        }
    }
}