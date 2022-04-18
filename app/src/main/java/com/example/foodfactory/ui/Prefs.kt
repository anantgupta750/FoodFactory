package com.example.foodfactory.ui

import android.content.Context
import android.content.SharedPreferences
import com.example.foodfactory.model.Dish

class Prefs(private val context: Context) {
    private var sp: SharedPreferences = context.getSharedPreferences("Order", Context.MODE_PRIVATE)
    fun addOrder(dish: Dish, qty: Int) {
        sp.edit().putString(dish.name, "${dish.name},${qty},${dish.price},${dish.veg},${dish.image}").apply()
    }

    fun updateOrder(dish: Dish, qty: Int) {
        val data = sp.getString(dish.name, "")
        if (data?.isEmpty() == false) {
            sp.edit().putString(dish.name, "${dish.name},${qty},${dish.price},${dish.veg},${dish.image}").apply()
        }
    }

    fun deleteOrder(name:String) {
        sp.edit().remove(name).apply()
    }

    fun viewOrders(): MutableMap<String, String>? {
        return sp.all as MutableMap<String, String>?
    }
}