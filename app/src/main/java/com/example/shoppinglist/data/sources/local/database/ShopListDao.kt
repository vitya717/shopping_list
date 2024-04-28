package com.example.shoppinglist.data.sources.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.data.sources.local.entities.ShopListItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_list_item")
    fun observeAll(): Flow<List<ShopListItemEntity>>

    @Query("SELECT * FROM shop_list_item")
    fun getShopLists(): List<ShopListItemEntity>

    @Insert
    fun addShopListItem(item: ShopListItemEntity)

    @Update
    fun updateShopListItem(item: ShopListItemEntity)

    @Query("DELETE FROM shop_list_item WHERE id = :id")
    suspend fun deleteShopListItemById(id: Long?): Int

    @Query("DELETE FROM shop_list_item")
    suspend fun deleteAll()

}