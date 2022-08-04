package com.project.squareempdirectory.retrofit.model

import retrofit2.http.GET

/**
 * Communicates with the http endpoint backend to obtain employee data.
 */


interface SquareEmployeeService {

    /**
     * @return [SquareEmployeeResponse] : List of employees from the server endpoint
     */

    @GET("employees.json")
    suspend fun getEmployeesList(
    ): SquareEmployeeResponse

    /**
     * @return [SquareEmployeeResponse] : Empty list of employees from the server endpoint
     */

    @GET("employees_empty.json")
    suspend fun getEmptyEmployeesList(
    ): SquareEmployeeResponse

    /**
     * @return [SquareEmployeeResponse] : Malformed list of employees from the server endpoint
     */

    @GET("employees_malformed.json")
    suspend fun getMalformedEmployeesList(
    ): SquareEmployeeResponse

}