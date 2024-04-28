package com.example.shoppinglist.data.repositories

import com.example.shoppinglist.data.models.ShopListItem
import com.example.shoppinglist.data.models.toExternal
import com.example.shoppinglist.data.models.toLocal
import com.example.shoppinglist.data.sources.local.database.ShopListDao
import com.example.shoppinglist.data.sources.local.entities.ShopListItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ShopListsRepository(private val shopListDao: ShopListDao) : ShopListRepositoryInterface {

    override fun getShopListItemsStream(): Flow<List<ShopListItem>> {
        return shopListDao.observeAll()
            .map { it.toExternal() }
    }

    override suspend fun addShopListItem(name: String) {
        withContext(Dispatchers.IO) {
            shopListDao.addShopListItem(
                ShopListItemEntity(
                    name = name,
                    isBought = false
                )
            )
        }
    }

    override suspend fun updateShopListItem(item: ShopListItem) {
        withContext(Dispatchers.IO) {
            shopListDao.updateShopListItem(item.toLocal())
        }
    }

    override suspend fun clearShopList() {
        withContext(Dispatchers.IO) {
            shopListDao.deleteAll()
        }
    }

    override suspend fun deleteShopListItemById(id: Long?) {
        withContext(Dispatchers.IO) {
            shopListDao.deleteShopListItemById(id)
        }
    }
}