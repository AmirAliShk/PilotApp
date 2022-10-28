package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.database.LocationsTable
import com.example.myapplication.database.MyRoom
import com.example.myapplication.databinding.FragmentAddPointBinding

class AddPointFragment(var east: String, var north: String, var elevation: String) : Fragment() {

    private lateinit var binding: FragmentAddPointBinding
    var database = MyRoom
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPointBinding.inflate(inflater, container, false)

        binding.edtEast.setText(east)
        binding.edtNorth.setText(north)
        binding.edtElevation.setText(elevation)

        binding.btnAdd.setOnClickListener {

        }

        return binding.root
    }


}