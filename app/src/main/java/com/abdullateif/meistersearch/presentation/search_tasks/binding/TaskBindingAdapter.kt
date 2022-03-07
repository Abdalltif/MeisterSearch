package com.abdullateif.meistersearch.presentation.search_tasks.binding

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdullateif.meistersearch.adapter.TaskAdapter
import com.abdullateif.meistersearch.common.UIState
import com.abdullateif.meistersearch.domain.entity.TaskDetails
import com.abdullateif.meistersearch.presentation.search_tasks.SearchTasksState

@BindingAdapter("tasks")
fun setRecyclerViewProperties(recyclerView: RecyclerView, state: SearchTasksState?) {
    if (recyclerView.adapter is TaskAdapter) {
        state?.let { state ->
            when (state.uiState) {
                UIState.DATA -> (recyclerView.adapter as TaskAdapter).setTaskList(state.tasks as List<TaskDetails>)
                else -> (recyclerView.adapter as TaskAdapter).setTaskList(emptyList())
            }
        }
    }
}

@BindingAdapter("state")
fun LinearLayout.setVisibility(state: SearchTasksState?) {
    state?.let { taskState ->
        when (taskState.uiState) {
            UIState.LOADING -> this.visibility = View.GONE
            UIState.DATA -> {
                if (taskState.tasks!!.isEmpty())
                    this.visibility = View.VISIBLE
                else
                    this.visibility = View.GONE
            }
            UIState.ERROR -> this.visibility = View.VISIBLE
            UIState.IDLE -> this.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("state")
fun ProgressBar.setVisibility(state: SearchTasksState?) {
    state?.let { state ->
        when (state.uiState) {
            UIState.LOADING -> this.visibility = View.VISIBLE
            UIState.DATA -> this.visibility = View.GONE
            UIState.ERROR -> this.visibility = View.GONE
            UIState.IDLE -> this.visibility = View.GONE
        }
    }
}