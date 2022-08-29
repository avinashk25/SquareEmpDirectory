package com.project.squareempdirectory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.project.squareempdirectory.databinding.ActivityEmployeeDetailsBinding
import com.project.squareempdirectory.ui.EmployeeDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmployeeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_details)
        binding.lifecycleOwner = this
        val extras = intent.extras

        val bundle = Bundle().apply {
            putSerializable(EmployeeDetailsFragment.employeeObject, extras?.getSerializable(EmployeeDetailsFragment.employeeObject))
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
            EmployeeDetailsFragment::class.java, bundle
        ).commitNow()
    }
}