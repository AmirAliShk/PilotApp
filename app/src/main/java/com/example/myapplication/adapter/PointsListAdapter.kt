package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.app.MyApplication
import com.example.myapplication.databinding.ListItemBinding
import com.example.myapplication.model.PointsModel

class PointsListAdapter(private val list: ArrayList<PointsModel>) :
    RecyclerView.Adapter<PointsListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(MyApplication.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.binding.txtRow.text = model.row
        holder.binding.txtName.text = model.name
        holder.binding.txtDescription.text = model.description
        holder.binding.txtEast.text = model.east
        holder.binding.txtNorth.text = model.north
        holder.binding.txtElevation.text = model.elevation
    }

    override fun getItemCount(): Int {
        return list.size
    }
}