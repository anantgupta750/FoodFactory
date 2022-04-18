package com.example.foodfactory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfactory.adapter.OrderAdapter
import com.example.foodfactory.databinding.FragmentOrderBinding
import com.example.foodfactory.model.OrderView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OrderFragment : Fragment(), OrderClickListener {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: Prefs
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        prefs = Prefs(requireContext())
        return root
    }

    fun load_orders() {
        val viewOrders = arrayListOf<OrderView>()
        prefs.viewOrders()?.forEach {
            val order = it.value.toString().split(",")
            if (order.size == 5) {
                viewOrders.add(
                    OrderView(
                        order[0],
                        order[2].toInt(),
                        order[1].toInt(),
                        order[4],
                        order[3].toBoolean()
                    )
                )
            } else {
                viewOrders.add(
                    OrderView(
                        order[0],
                        order[2].toInt(),
                        order[1].toInt(),
                        "",
                        false
                    )
                )
            }
        }
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())
        val adapter: OrderAdapter? = viewOrders?.let { OrderAdapter(viewOrders, this) }
        binding.recyclerViewCart.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = Prefs(requireContext())
        load_orders()

        binding.btnCheckout.setOnClickListener {
            val dir = OrderFragmentDirections.actionOrderFragmentToPaymentFragment()
            //clear the prefs
            findNavController().navigate(dir)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCellClickListener(view: View, holder: RecyclerView.ViewHolder) {
        val orderView = view.tag as OrderView
        prefs.deleteOrder(orderView.name)
    }

}