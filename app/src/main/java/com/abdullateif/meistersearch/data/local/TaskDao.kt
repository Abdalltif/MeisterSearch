package com.abdullateif.meistersearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdullateif.meistersearch.common.Config
import com.abdullateif.meistersearch.domain.entity.TaskDetails

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(task: List<TaskDetails>)

    @Query("SELECT * FROM ${Config.DB_TABLE_NAME} where taskName like :query OR projectName like :query")
    suspend fun searchTasks(query: String): List<TaskDetails>

    @Query("SELECT * FROM ${Config.DB_TABLE_NAME} where (taskName like :query OR projectName like :query) AND status = :status")
    suspend fun searchTasks(query: String, status: Int): List<TaskDetails>

}