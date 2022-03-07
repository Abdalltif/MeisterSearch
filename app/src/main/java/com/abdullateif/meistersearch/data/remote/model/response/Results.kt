package com.abdullateif.meistersearch.data.remote.model.response


import com.abdullateif.meistersearch.data.remote.dto.ProjectDto
import com.abdullateif.meistersearch.data.remote.dto.SectionDto
import com.abdullateif.meistersearch.data.remote.dto.TaskDto
import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("projects")
    val projects: List<ProjectDto>,
    @SerializedName("sections")
    val sections: List<SectionDto>,
    @SerializedName("tasks")
    val tasks: List<TaskDto>
)