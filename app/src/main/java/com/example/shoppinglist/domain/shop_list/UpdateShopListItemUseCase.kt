package com.example.shoppinglist.domain.shop_list

import com.example.shoppinglist.data.models.ShopListItem
import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import javax.inject.Inject

class UpdateShopListItemUseCase @Inject constructor(
    private val repository: ShopListRepositoryInterface
) {

    suspend fun execute(item: ShopListItem) {
        repository.updateShopListItem(item = item)
    }
}