package com.abdullateif.meistersearch.common

import androidx.databinding.adapters.SearchViewBindingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceQueryChangeListener(
    delay: Long,
    scope: CoroutineScope,
    private val onDebouncingQueryTextChange: (String?) -> Unit
) : SearchViewBindingAdapter.OnQueryTextChange {
    var debounceTime = delay

    private val coroutineScope = scope

    private var searchJob: Job? = null

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debounceTime)
                onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }
}