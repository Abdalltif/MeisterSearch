package com.abdullateif.meistersearch.data.remote.model

enum class FilterType(val status: Int?) {
    ALL(null),
    ACTIVE(1),
    ARCHIVED(18)
}