package com.example.shoppinglist.data.repositories

import com.example.shoppinglist.data.models.ShopListItem
import kotlinx.coroutines.flow.Flow

interface ShopListRepositoryInterface {
    fun getShopListItemsStream() : Flow<List<ShopListItem>>
    suspend fun addShopListItem(name: String)
    suspend fun updateShopListItem(item: ShopListItem)
    suspend fun deleteShopListItemById(id: Long?)
    suspend fun clearShopList()
}