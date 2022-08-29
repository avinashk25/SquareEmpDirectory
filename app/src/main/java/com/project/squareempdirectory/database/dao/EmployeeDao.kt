package com.project.squareempdirectory.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.squareempdirectory.retrofit.model.EmployeesListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM employee ORDER BY uuid ASC")
    fun getEmployeeList(): Flow<List<EmployeesListItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(employee: EmployeesListItem)

    @Query("DELETE FROM employee")
    suspend fun deleteAll()
}