package com.abdullateif.meistersearch.domain.repository
import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.data.repository.Resource
import com.abdullateif.meistersearch.domain.entity.TaskDetails

interface TaskRepository {
    suspend fun searchTasks(
        query: String,
        filterType: FilterType,
    ): Resource<List<TaskDetails>>
}