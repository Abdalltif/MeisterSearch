package com.abdullateif.meistersearch.data.remote.model.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("paging")
    val paging: Paging,
    @SerializedName("results")
    val data: Results
)