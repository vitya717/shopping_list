package com.example.shoppinglist.data.models

data class ShopList(
    val items: List<ShopListItem>,
    val allItemsCount: Int,
    val boughtItemsCount: Int
)
