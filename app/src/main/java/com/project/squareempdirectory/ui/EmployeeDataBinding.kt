package com.project.squareempdirectory.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.squareempdirectory.R
import com.project.squareempdirectory.retrofit.model.EmployeesListItem

/**
 * Binding methods added to update UI items from layout files.
 */

@BindingAdapter("items")
fun RecyclerView.addItems(items : List<EmployeesListItem>?) {
    if (adapter == null) {
        val fragmentListAdapter = EmployeeListAdapter(items ?: emptyList())
        adapter = fragmentListAdapter
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    } else {
        (adapter as? EmployeeListAdapter)?.let {
            if (items != null) {
                it.items = items
            }
            it.notifyDataSetChanged()
        }
    }
}

@BindingAdapter("imageSrc")
fun ImageView.setImageSrc(imageSrc: String?) {
    Glide.with(this)
        .load(imageSrc)
        .circleCrop()
        .placeholder(R.drawable.default_image)
        .error(R.drawable.default_image)
        .into(this)
}