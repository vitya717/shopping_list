package com.example.shoppinglist.data.models

import kotlinx.datetime.LocalDateTime

data class Note(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val lastUpdatedDateTime: LocalDateTime
)
