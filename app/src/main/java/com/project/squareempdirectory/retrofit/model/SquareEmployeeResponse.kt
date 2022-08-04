package com.project.squareempdirectory.retrofit.model

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

data class EmployeesListItem(

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,

    @field:SerializedName("email_address")
    val emailAddress: String,

    @field:SerializedName("employee_type")
    val employeeType: String,

    @field:SerializedName("biography")
    val biography: String,

    @field:SerializedName("team")
    val team: String,

    @field:SerializedName("photo_url_large")
    val photoUrlLarge: String,

    @field:SerializedName("photo_url_small")
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
