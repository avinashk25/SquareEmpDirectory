package com.project.squareempdirectory.database.provider

import android.content.Context
import com.project.squareempdirectory.database.dao.EmployeeDao
import com.project.squareempdirectory.database.db.EmployeeRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseProvider {

    @Provides
    fun providesEmployeeDbDao(@ApplicationContext context: Context) : EmployeeDao {
        return EmployeeRoomDatabase.getDatabase(context).employeeDao()
    }
}