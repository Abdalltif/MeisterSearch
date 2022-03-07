package com.abdullateif.meistersearch.adapter

import AppDiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullateif.meistersearch.databinding.TaskItemLayoutBinding
import com.abdullateif.meistersearch.domain.entity.TaskDetails

class TaskAdapter(
    private val listenerTaskItem: OnTaskItemClickListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = emptyList<TaskDetails>()

    inner class TaskViewHolder(val binding: TaskItemLayoutBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listenerTaskItem.onTaskItemClick(taskList[position])
        }
    }

    interface OnTaskItemClickListener {
        fun onTaskItemClick(task: TaskDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.apply {
            task = taskList[position]
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setTaskList(newList: List<TaskDetails>){
        val diffUtil = AppDiffUtil(taskList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        taskList  = newList
        diffResults.dispatchUpdatesTo(this)
    }
}