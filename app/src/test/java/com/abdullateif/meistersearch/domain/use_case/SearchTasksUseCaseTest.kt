package com.abdullateif.meistersearch.domain.use_case

import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.data.repository.Resource
import com.abdullateif.meistersearch.domain.entity.TaskDetails
import com.abdullateif.meistersearch.domain.repository.TaskRepository
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchTasksUseCaseTest {
    lateinit var useCase: SearchTasksUseCase

    val taskRepository: TaskRepository = mock()

    @Before
    fun setup() {
        useCase = SearchTasksUseCase(taskRepository)
    }

    @Test
    fun `fetch tasks from repository`() = runBlocking {
        val mockTasks = MockTaskProvider.getMockTasks()
        val expectedResult = Resource.Success(data = mockTasks)
        whenever(taskRepository.searchTasks("ask", FilterType.ACTIVE))
            .thenReturn(expectedResult as Resource<List<TaskDetails>>)

        val resource = useCase("ask", FilterType.ACTIVE)

        verify(taskRepository).searchTasks("ask", FilterType.ACTIVE)
        assertThat(resource).isEqualTo(expectedResult)
    }
}