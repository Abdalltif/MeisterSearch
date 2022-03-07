package com.abdullateif.meistersearch.data.remote.dto

import com.abdullateif.meistersearch.domain.entity.Project

data class ProjectDto(
    val id: Long,
    val name: String
)

fun ProjectDto.toProject(): Project =
    Project(
        id = id,
        name = name
    )
