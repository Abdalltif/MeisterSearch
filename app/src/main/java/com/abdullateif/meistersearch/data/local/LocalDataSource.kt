package com.abdullateif.meistersearch.data.local

import com.abdullateif.meistersearch.domain.entity.TaskDetails
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: TaskDao) {

    suspend fun searchTasks(query: String, status: Int?): List<TaskDetails> {
        return if (status != null) {
            dao.searchTasks("%$query%", status)
        } else {
            dao.searchTasks("%$query%")
        }
    }

    suspend fun insertTasks(tasks: List<TaskDetails>) {
        dao.insertTasks(tasks)
    }

}