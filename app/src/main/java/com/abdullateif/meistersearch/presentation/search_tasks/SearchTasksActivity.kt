package com.abdullateif.meistersearch.presentation.search_tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullateif.meistersearch.R
import com.abdullateif.meistersearch.data.remote.model.FilterType
import com.abdullateif.meistersearch.databinding.ActivityMainBinding
import com.abdullateif.meistersearch.domain.entity.TaskDetails
import com.abdullateif.meistersearch.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchTasksActivity : AppCompatActivity(),
    TaskAdapter.OnTaskItemClickListener{

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SearchTasksViewModel by viewModels()
    private val taskAdapter by lazy { TaskAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        setSupportActionBar(binding.toolbar)
        initChipGroup()
        initTaskRecyclerView()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { _, filterId ->
            when (filterId) {
                binding.chipAll.id -> viewModel.setFilter(FilterType.ALL)
                binding.chipActive.id -> viewModel.setFilter(FilterType.ACTIVE)
                binding.chipArchived.id -> viewModel.setFilter(FilterType.ARCHIVED)
            }
        }
    }

    private fun initTaskRecyclerView() {
        binding.rvTask.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = taskAdapter
        }
    }

    override fun onTaskItemClick(task: TaskDetails) = AlertDialog.Builder(this)
        .setTitle(getString(R.string.dialog_title))
        .setMessage(task.taskName)
        .create()
        .show()
}