package com.example.studymytaskapp.tasks

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.studymytaskapp.data.Task

/**
 * BindingAdapter for the Tasks list.
 */
@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<Task>?) {
    items?.let {
        (listView.adapter as TasksListAdapter).submitList(items)
    }
}

@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(liveData: LiveData<List<Task>>) {
    val data = liveData.value
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}