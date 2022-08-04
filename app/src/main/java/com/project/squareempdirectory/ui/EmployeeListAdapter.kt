package com.project.squareempdirectory.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.squareempdirectory.databinding.EmployeeListItemBinding
import com.project.squareempdirectory.retrofit.model.EmployeesListItem


/**
 * A RecyclerView.Adapter populating the screen with a list of employees fetched through viewModel using retrofit Http API.
 */

class EmployeeListAdapter(var items: List<EmployeesListItem> = emptyList()) : RecyclerView.Adapter<EmployeeListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        val binding = EmployeeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeListViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        holder.binding.employeeDetails = items.elementAtOrNull(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

/**
 * Holds the view for the Employee information item.
 */

class EmployeeListViewHolder(val binding: EmployeeListItemBinding, itemView : View) : RecyclerView.ViewHolder(itemView)