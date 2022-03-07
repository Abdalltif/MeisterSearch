package com.abdullateif.meistersearch.domain.entity

import com.google.gson.annotations.SerializedName

data class Task(
    val id: Long,
    val name: String,
    val status: Int,
    @SerializedName("section_id") val sectionId: Long
)
