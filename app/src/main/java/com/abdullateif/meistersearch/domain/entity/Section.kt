package com.abdullateif.meistersearch.domain.entity

import com.google.gson.annotations.SerializedName

data class Section(
    val id: Long,
    val name:String,
    @SerializedName("project_id") val projectId: Long
)
