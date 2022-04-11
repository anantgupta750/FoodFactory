package com.example.foodfactory.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfactory.R
import com.example.foodfactory.adapter.subMenuAdapter
import com.example.foodfactory.databinding.FragmentSubMenuBinding
import com.example.foodfactory.model.Dish
import com.example.foodfactory.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SubMenuFragment : Fragment(), CellClickListener {
    private lateinit var orders: ArrayList<Order>
    private val TAG: String = "MEH-MEH"
    private var _binding: FragmentSubMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var submenuadapter: subMenuAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var submenuArraylist: ArrayList<Dish>
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        return root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = SubMenuFragmentArgs.fromBundle(requireArguments())

        super.onViewCreated(view, savedInstanceState)
        load_SubMenu(args.name)
        binding.menname.text = args.name
        binding.recyclerViewSubMenu.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewSubMenu.setHasFixedSize(true)
        submenuArraylist = arrayListOf()
        orders = arrayListOf<Order>()

        submenuadapter = subMenuAdapter(submenuArraylist, this)

        binding.recyclerViewSubMenu.adapter = submenuadapter
        binding.button.setOnClickListener {
            orders.forEach { order ->
                auth.currentUser?.let { it1 ->
                    val dateTime = LocalDateTime.now()
                    val datestr = dateTime.format(DateTimeFormatter.ofPattern("MdyH"))
                    db.collection("orders").document(it1.uid).collection(datestr)
                        .add(order).addOnSuccessListener {
                            Toast.makeText(requireContext(), "order updated", Toast.LENGTH_LONG)
                                .show()
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun load_SubMenu(name: String) {
        val docRef =
            db.collection("Menu-Collection").document(name.toLowerCase()).collection("items")
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                submenuArraylist.clear()
                for (doc in document) {
                    try {
                        submenuArraylist.add(doc.toObject<Dish>())
                    } catch (e: Exception) {

                    }
                }
                submenuadapter.notifyDataSetChanged()
            } else {
                Log.d(TAG, "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with", exception)
        }
    }

    override fun onCellClickListener(view: View, holder: subMenuAdapter.MyViewHolder) {
        val dish = view.tag as Dish
        when (view.id) {
            R.id.bmin -> {
                orders.forEach {
                    if (it.dish == dish) {
                        it.qty--
                        holder.qty.text = it.qty.toString()
                        if (it.qty == 0) {
                            holder.qtycartbtn.visibility = View.INVISIBLE
                            holder.btn.visibility = View.VISIBLE
                        }
                    }
                }
            }
            R.id.bplus -> {
                orders.forEach {
                    if (it.dish == dish) {
                        it.qty++
                        holder.qty.text = it.qty.toString()
                    }
                }
            }
            R.id.cartAddButton -> {
                orders.add(Order(dish, 1, auth.currentUser?.uid ?: "", "", ""))
                orders.forEach {
                    if (it.dish == dish) {
                        it.qty = 1
                        holder.qty.text = it.qty.toString()
                    }
                }
            }
        }
        Log.d("orders", orders.toString())
    }

}

interface CellClickListener {
    fun onCellClickListener(view: View, holder: subMenuAdapter.MyViewHolder)
}


