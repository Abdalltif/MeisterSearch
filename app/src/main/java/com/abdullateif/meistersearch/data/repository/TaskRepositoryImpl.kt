package com.abdullateif.meistersearch.data.repository

import com.abdullateif.meistersearch.domain.repository.TaskRepository
import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.data.local.LocalDataSource
import com.abdullateif.meistersearch.data.remote.TaskApi
import com.abdullateif.meistersearch.data.remote.model.TaskRequestObject
import com.abdullateif.meistersearch.data.remote.model.response.Results
import com.abdullateif.meistersearch.di.DispatcherModule
import com.abdullateif.meistersearch.domain.entity.TaskDetails
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi,
    private val localDataSource: LocalDataSource,
    @DispatcherModule.IoDispatcher
        private val ioDispatcher: CoroutineDispatcher
    ) : TaskRepository {

    override suspend fun searchTasks(
        query: String,
        filterType: FilterType
    ): Resource<List<TaskDetails>> {
        return withContext(ioDispatcher) {
            try {

                val results = api.searchTasks(
                    filter = Gson().toJson(TaskRequestObject(query, filterType.status))
                ).data

                val tasks = mapToTaskDetails(results)

                localDataSource.insertTasks(tasks)

                Resource.Success(tasks)

            } catch (exception: HttpException) {
                Resource.Error(exception.localizedMessage ?: "An error occurred")
            } catch (exception: IOException) {
                val tasks = localDataSource.searchTasks(query, filterType.status)
                Resource.Success(tasks)
            }
        }
    }

    private fun mapToTaskDetails(data: Results): List<TaskDetails> {
        val taskItems = data.tasks.map { task ->
            val sectionId = task.sectionId
            val sectionInfo = data.sections.find { sectionId == it.id }
            val projectInfo = data.projects.find { sectionInfo?.projectId == it.id }
            TaskDetails(
                taskId = task.id,
                taskName = task.name,
                projectName = projectInfo?.name ?: "",
                status = task.status,
            )
        }
        return taskItems
    }

}