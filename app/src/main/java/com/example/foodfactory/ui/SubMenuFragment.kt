package com.example.foodfactory.ui

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfactory.adapter.subMenuAdapter
import com.example.foodfactory.databinding.FragmentSubMenuBinding
import com.example.foodfactory.model.Dish
import com.google.firebase.firestore.FirebaseFirestore

class SubMenuFragment : Fragment() {
    private var _binding: FragmentSubMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var submenuadapter : subMenuAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var submenuArraylist : ArrayList<Dish>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSubMenu.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        submenuArraylist = arrayListOf()

        submenuadapter = subMenuAdapter(submenuArraylist)

        recyclerView.adapter = submenuadapter

        db=FirebaseFirestore.getInstance()
        val ref= db.document("Doc_Menu-123").collection("<>")
        ref.get().
        addOnSuccessListener{ document ->
            if(document != null)
            {
                Log.d(ContentValues.TAG, "CollectionSnapshot data: ${document}")
            }
            else
            {
                Log.d(ContentValues.TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with", exception)
            }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}