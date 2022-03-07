package com.abdullateif.meistersearch.data.remote

import com.abdullateif.meistersearch.data.remote.model.response.SearchResponse
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TaskApi {

    @POST("/search")
    suspend fun searchTasks(
        @Query("filter") filter: String,
        @Query("response_format") responseFormat: String = "object"
    ): SearchResponse

}