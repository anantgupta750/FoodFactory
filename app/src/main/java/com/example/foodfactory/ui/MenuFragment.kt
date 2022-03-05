package com.example.foodfactory.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfactory.adapter.MenuAdapter
import com.example.foodfactory.databinding.FragmentMenuBinding
import com.example.foodfactory.model.Category
import com.google.firebase.firestore.FirebaseFirestore

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var menuArraylist : ArrayList<Category>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        menuArraylist = arrayListOf()

        menuAdapter = MenuAdapter(menuArraylist)

        recyclerView.adapter = menuAdapter

        db=FirebaseFirestore.getInstance()
        //Create the new functions according to the new documentation in the (sub menu fragment too)\

        val docRef=db.collection("Menu-Collection").document("Doc_menu-123")
        docRef.get().
        addOnSuccessListener { document ->
            if(document != null)
            {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            }
            else
            {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with", exception)
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}