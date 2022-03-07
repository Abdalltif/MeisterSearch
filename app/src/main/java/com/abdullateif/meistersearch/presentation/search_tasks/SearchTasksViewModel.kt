package com.abdullateif.meistersearch.presentation.search_tasks

import androidx.lifecycle.*
import com.abdullateif.meistersearch.common.Config
import com.abdullateif.meistersearch.common.DebounceQueryChangeListener
import com.abdullateif.meistersearch.common.UIState
import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.data.repository.Resource
import com.abdullateif.meistersearch.domain.use_case.SearchTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchTasksViewModel @Inject constructor(
    private val searchTasksUseCase: SearchTasksUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SearchTasksState>()
    val state: LiveData<SearchTasksState> = _state

    private var query = ""
    private var filterType = FilterType.ALL

    val onQueryTextChange = DebounceQueryChangeListener(
        Config.DEBOUNCE_DURATION,
        viewModelScope
    ) {
        it?.let { text ->
            val newText = text.trim()
            if (newText != query) {
                query = newText
                searchTasks()
            }
        }
    }

    private fun searchTasks() {
        if (query.isEmpty()) {
            _state.value = SearchTasksState(
                uiState = UIState.DATA,
                tasks = emptyList()
            )
            return
        }

        _state.value = SearchTasksState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch {
            delay(Config.DEBOUNCE_DURATION)
            val resource = searchTasksUseCase(
                query = query,
                filterType = filterType,
            )
            _state.value = when (resource) {
                is Resource.Success ->
                    SearchTasksState(
                        uiState = UIState.DATA,
                        tasks = resource.data
                    )
                else ->
                    SearchTasksState(
                        uiState = UIState.ERROR
                    )
            }
        }
    }

    fun setFilter(status: FilterType) {
        this.filterType = status
        searchTasks()
    }

    fun setQuery(query: String) {
        this.query = query
        searchTasks()
    }
}