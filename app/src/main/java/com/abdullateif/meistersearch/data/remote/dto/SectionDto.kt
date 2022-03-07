package com.abdullateif.meistersearch.data.remote.dto

import com.abdullateif.meistersearch.domain.entity.Project
import com.abdullateif.meistersearch.domain.entity.Section
import com.google.gson.annotations.SerializedName

data class SectionDto(
    val id: Long,
    val name:String,
    @SerializedName("project_id") val projectId: Long
)

fun SectionDto.toSection(): Section =
    Section(
        id = id,
        name = name,
        projectId = projectId
    )