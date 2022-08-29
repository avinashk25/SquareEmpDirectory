package com.project.squareempdirectory.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.squareempdirectory.EmployeeDetailsActivity
import com.project.squareempdirectory.databinding.EmployeeListFragmentBinding
import com.project.squareempdirectory.retrofit.model.EmployeesListItem
import com.project.squareempdirectory.viewmodel.EmployeeListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Displays the list of Employees with photo, name, and team.
 */

@AndroidEntryPoint
class EmployeeListFragment : Fragment() {

    companion object {
        const val employeeObject = "employee_details"
    }

    private lateinit var binding: EmployeeListFragmentBinding
    private val viewModel : EmployeeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmployeeListFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        viewModel.items.observe(viewLifecycleOwner, ::onEmployeeListItemLoaded)
        viewModel.isEmpty.observe(viewLifecycleOwner, ::updateEmptyListState)
        viewModel.itemClick.observe(viewLifecycleOwner, ::onEmployeeItemClick)
        return binding.root
    }

    /**
     * Check if empty list is obtained to show appropriate text display to the user
     * @param list list of employees
     */

    private fun onEmployeeListItemLoaded(list: List<EmployeesListItem>?) {
        if (!list.isNullOrEmpty()) {
            return
        } else {
            updateEmptyListState(true)
        }
    }

    private fun updateEmptyListState(isListEmpty : Boolean) {
        if (isListEmpty) {
            binding.isEmpty = isListEmpty
        }
    }

    private fun onEmployeeItemClick(employeesListItem: EmployeesListItem) {
        val intent = Intent(context, EmployeeDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(employeeObject, employeesListItem)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}