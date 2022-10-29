package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.activity.MainActivity
import com.example.myapplication.app.MyApplication
import com.example.myapplication.database.LocationsTable
import com.example.myapplication.databinding.FragmentAddPointBinding

class AddPointFragment(var east: String, var north: String, var elevation: String) : Fragment() {

    private lateinit var binding: FragmentAddPointBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPointBinding.inflate(inflater, container, false)

        binding.edtEast.setText(east)
        binding.edtNorth.setText(north)
        binding.edtElevation.setText(elevation)

        binding.btnAdd.setOnClickListener {
            val loc = LocationsTable(
                0,
                binding.edtName.text.toString(),
                binding.edtDescription.text.toString(),
                binding.edtEast.text.toString(),
                binding.edtNorth.text.toString(),
                binding.edtElevation.text.toString()
            )
            MainActivity.dataBase.locationsDao().insertLocation(loc)
            Toast.makeText(
                MyApplication.context,
                "Coordinator ${binding.edtName.text} added successfully",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.imgBack.setOnClickListener {
            MyApplication.currentActivity.onBackPressed()
        }

        return binding.root
    }
}