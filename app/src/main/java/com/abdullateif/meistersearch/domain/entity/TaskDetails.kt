package com.abdullateif.meistersearch.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdullateif.meistersearch.common.Config

@Entity(tableName = Config.DB_TABLE_NAME)
data class TaskDetails(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long,
    val taskName: String,
    val projectName: String,
    val status: Int,
)