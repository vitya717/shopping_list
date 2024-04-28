package com.example.shoppinglist.data.models

import androidx.room.PrimaryKey

data class ShopListItem(
    val id: Long?,
    val name: String,
    val isBought: Boolean
)