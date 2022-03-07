package com.abdullateif.meistersearch.domain.use_case

import com.abdullateif.meistersearch.domain.repository.TaskRepository
import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.data.repository.Resource
import com.abdullateif.meistersearch.domain.entity.TaskDetails
import javax.inject.Inject

class SearchTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(
        query: String,
        filterType: FilterType
    ): Resource<List<TaskDetails>> = repository.searchTasks(query, filterType)

}