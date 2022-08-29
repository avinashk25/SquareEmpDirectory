package com.project.squareempdirectory.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.squareempdirectory.database.dao.EmployeeDao
import com.project.squareempdirectory.retrofit.model.EmployeesListItem

@Database(entities = [EmployeesListItem::class], version = 1)
abstract class EmployeeRoomDatabase : RoomDatabase() {

    abstract fun employeeDao():EmployeeDao

    companion object {

        @Volatile
        private var INSTANCE : EmployeeRoomDatabase? = null

        fun getDatabase( context: Context) : EmployeeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeRoomDatabase::class.java,
                    "employee_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}