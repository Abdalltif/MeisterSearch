package com.abdullateif.meistersearch.presentation.search_tasks

import com.abdullateif.meistersearch.common.UIState
import com.abdullateif.meistersearch.domain.entity.Task
import com.abdullateif.meistersearch.domain.entity.TaskDetails

data class SearchTasksState(
    val uiState: UIState = UIState.IDLE,
    val tasks: List<TaskDetails>? = null
)