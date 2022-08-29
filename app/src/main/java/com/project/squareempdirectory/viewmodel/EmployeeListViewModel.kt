package com.project.squareempdirectory.viewmodel

import androidx.lifecycle.*
import com.project.squareempdirectory.SingleLiveEvent
import com.project.squareempdirectory.database.repository.EmployeeRepository
import com.project.squareempdirectory.retrofit.model.EmployeesListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the list of employees to be displayed.
 * This calls HTTP API through service API which is injected and updates LiveData value being observed in the fragment.
 */

@HiltViewModel
class EmployeeListViewModel @Inject constructor(private val repository: EmployeeRepository) : ViewModel() {

    // LiveData variable to track list of schools to be displayed on UI
    private val _items = MutableLiveData<List<EmployeesListItem>>()
    val items : LiveData<List<EmployeesListItem>> = _items

    // LiveData variable to track if empty list of schools is returned sp that appropriate message is displayed on UI
    private val _isEmpty = MutableLiveData(false)
    val isEmpty : LiveData<Boolean> = _isEmpty

    // LiveData variable to refresh status update
    private val _isRefreshing = SingleLiveEvent<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    val employeeList: LiveData<List<EmployeesListItem?>> = repository.employeeList.asLiveData()

    init {
        loadEmployeeList()
    }

    /**
     * Load initial list of employees obtained from the endpoint.
     */

    fun loadEmployeeList() {
        viewModelScope.launch(Dispatchers.IO ) {
            kotlin.runCatching {
                repository.getEmployeesList()
                //service.getEmptyEmployeesList().employeesList
                //service.getMalformedEmployeesList().employeesList
            }.onFailure {
                _items.postValue(listOf())
                _isEmpty.postValue(true)
            }.onSuccess {
                _items.postValue(it)
            }
            _isRefreshing.postValue(false)
        }
    }
}