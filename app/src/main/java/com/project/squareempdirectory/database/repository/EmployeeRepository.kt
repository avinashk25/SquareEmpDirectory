package com.project.squareempdirectory.database.repository

import androidx.annotation.WorkerThread
import com.project.squareempdirectory.database.dao.EmployeeDao
import com.project.squareempdirectory.retrofit.model.EmployeesListItem
import com.project.squareempdirectory.retrofit.model.SquareEmployeeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepository @Inject constructor(private val service: SquareEmployeeService, private val employeeDao: EmployeeDao) {

    val employeeList: Flow<List<EmployeesListItem>> = employeeDao.getEmployeeList()

    @WorkerThread
    suspend fun insert(employeeEntity: EmployeesListItem) {
        employeeDao.insert(employeeEntity)
    }

    suspend fun getEmployeesList() : List<EmployeesListItem> {
        var list : List<EmployeesListItem> = emptyList()
        //if (employeeList.first().isEmpty()) {
            list = service.getEmployeesList().employeesList.sortedBy { it.fullName.lowercase() }
        //}
        if (list.isNotEmpty()) {
            for (items in list) {
                insert(items)
            }
        }
        return list
    }
}