package com.project.squareempdirectory.retrofit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.project.squareempdirectory.BaseApplication
import com.project.squareempdirectory.R

/**
 * Endpoint response is received as list of employees.
 */
data class SquareEmployeeResponse(
    @field:SerializedName("employees")
    val employeesList: List<EmployeesListItem>
)

/**
 * Individual Employee information to be displayed.
 */

@Entity(tableName = "employee")
data class EmployeesListItem(

    @field:SerializedName("uuid")
    @PrimaryKey @ColumnInfo(name = "uuid")
    val uuid: String,

    @field:SerializedName("full_name")
    @ColumnInfo(name = "fullName")
    val fullName: String,

    @field:SerializedName("phone_number")
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,

    @field:SerializedName("email_address")
    @ColumnInfo(name = "emailAddress")
    val emailAddress: String,

    @field:SerializedName("employee_type")
    @ColumnInfo(name = "employeeType")
    val employeeType: String,

    @field:SerializedName("biography")
    @ColumnInfo(name = "biography")
    val biography: String,

    @field:SerializedName("team")
    @ColumnInfo(name = "team")
    val team: String,

    @field:SerializedName("photo_url_large")
    @ColumnInfo(name = "photoUrlLarge")
    val photoUrlLarge: String,

    @field:SerializedName("photo_url_small")
    @ColumnInfo(name = "photoUrlSmall")
    val photoUrlSmall: String,
)

/**
 * Enum class to display type of employment.
 */

enum class EmployeeType (val value : String) {
    PART_TIME(BaseApplication.getApplicationContext().getString(R.string.part_time_employee)),
    CONTRACTOR(BaseApplication.getApplicationContext().getString(R.string.contractor_employee)),
    FULL_TIME(BaseApplication.getApplicationContext().getString(R.string.full_time_employee))
}
