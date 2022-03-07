package com.abdullateif.meistersearch.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class Paging(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("results_per_page")
    val resultsPerPage: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)