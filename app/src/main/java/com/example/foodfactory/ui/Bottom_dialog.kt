package com.example.foodfactory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodfactory.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Bottom_dialog: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog, container, false)
    }
}