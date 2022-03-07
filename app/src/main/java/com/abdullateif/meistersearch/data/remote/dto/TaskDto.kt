package com.abdullateif.meistersearch.data.remote.dto

import com.abdullateif.meistersearch.domain.entity.Section
import com.abdullateif.meistersearch.domain.entity.Task
import com.google.gson.annotations.SerializedName

data class TaskDto(
    val id: Long,
    val name: String,
    val status: Int,
    @SerializedName("section_id") val sectionId: Long
)

fun TaskDto.toTask(): Task =
    Task(
        id = id,
        name = name,
        status = status,
        sectionId = sectionId
    )