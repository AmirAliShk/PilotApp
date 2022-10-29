package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.activity.MainActivity
import com.example.myapplication.adapter.PointsListAdapter
import com.example.myapplication.app.MyApplication
import com.example.myapplication.databinding.FragmentPointsListBinding
import com.example.myapplication.model.PointsModel

class PointsListFragment : Fragment() {

    lateinit var binding: FragmentPointsListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsListBinding.inflate(inflater, container, false)

        val dataArr = MainActivity.dataBase.locationsDao().getLocations()
        val pointsList = ArrayList<PointsModel>()
        for (i in dataArr.indices) {
            val data = dataArr[i]
            val model = PointsModel(
                data.recordId.toString(),
                data.name,
                data.description,
                data.east,
                data.north,
                data.elevation
            )
            pointsList.add(model)
        }
        val adapter = PointsListAdapter(pointsList)
        binding.pointList.adapter = adapter

        binding.imgBack.setOnClickListener {
            MyApplication.currentActivity.onBackPressed()
        }

        return binding.root
    }
}