package com.abdullateif.meistersearch.presentation.search_tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.abdullateif.meistersearch.common.UIState
import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.data.repository.Resource
import com.abdullateif.meistersearch.domain.use_case.SearchTasksUseCase
import com.abdullateif.meistersearch.utils.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SearchTasksViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: SearchTasksViewModel

    val searchTasksUseCase: SearchTasksUseCase = mock()

    @Mock
    private lateinit var uiObserver: Observer<SearchTasksState>

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `fetch tasks successfully should return null`() = runTest {
        val mockResponse = MockTaskProvider.getMockTasks()

        whenever(searchTasksUseCase("ask", FilterType.ACTIVE))
            .thenReturn(Resource.Success(data = mockResponse))

        viewModel = SearchTasksViewModel(searchTasksUseCase)

        assertThat(viewModel.state.value).isNull()
    }

    @Test
    fun `empty query should return null`() = runTest {
        val mockResponse = MockTaskProvider.getMockTasks()

        whenever(searchTasksUseCase("ask", FilterType.ACTIVE))
            .thenReturn(Resource.Success(data = mockResponse))

        viewModel = SearchTasksViewModel(searchTasksUseCase)
        viewModel.setQuery("")

        assertThat(viewModel.state.value).isNull()
    }

    @Test
    fun `fetch tasks should return loading state`() = runTest {
        val mockResponse = MockTaskProvider.getMockTasks()

        whenever(searchTasksUseCase("ask", FilterType.ACTIVE))
            .thenReturn(Resource.Success(data = mockResponse))

        viewModel = SearchTasksViewModel(searchTasksUseCase)

        viewModel.setQuery("ask")

        val value = viewModel.state.getOrAwaitValueTest()
        assertThat(value.uiState).isEqualTo(UIState.LOADING)
    }

    @Test
    fun `fetch tasks successfully should return data`() = runTest {
        val mockResponse = MockTaskProvider.getMockTasks()

        whenever(searchTasksUseCase("test", FilterType.ALL))
            .thenReturn(Resource.Success(data = mockResponse))

        viewModel = SearchTasksViewModel(searchTasksUseCase)

        viewModel.setQuery("test")

        viewModel.state.observeForever {
            when (it.uiState) {
                UIState.DATA ->
                    assertThat(viewModel.state.value).isEqualTo(
                        SearchTasksState(
                            uiState = UIState.DATA,
                            tasks = mockResponse
                        )
                    )
            }
        }
    }
}