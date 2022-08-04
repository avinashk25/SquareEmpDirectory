package com.project.squareempdirectory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.squareempdirectory.retrofit.model.EmployeesListItem
import com.project.squareempdirectory.retrofit.model.SquareEmployeeResponse
import com.project.squareempdirectory.retrofit.model.SquareEmployeeService
import com.project.squareempdirectory.viewmodel.EmployeeListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EmployeeListViewModelUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var service: SquareEmployeeService

    private lateinit var viewModel: EmployeeListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        viewModel = EmployeeListViewModel(service)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testLoadEmployeeList_emptyList() = runBlocking {
        coEvery { service.getEmployeesList() } returns SquareEmployeeResponse(emptyList())

        viewModel.loadEmployeeList()
        assertEquals(0, viewModel.items.value?.size)
    }

    @Test
    fun testLoadEmployeeList_fullList() = runBlocking {

        // dummy employee data
        val mockEmployeeList = listOf(mockk<EmployeesListItem>())
        coEvery { service.getEmployeesList() } returns SquareEmployeeResponse(mockEmployeeList)

        viewModel.loadEmployeeList()
        delay(100)
        assertEquals(mockEmployeeList, viewModel.items.value)
    }
}