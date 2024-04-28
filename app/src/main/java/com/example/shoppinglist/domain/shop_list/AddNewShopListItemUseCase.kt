package com.example.shoppinglist.domain.shop_list

import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import javax.inject.Inject

class AddNewShopListItemUseCase @Inject constructor(
    private val repository: ShopListRepositoryInterface
) {

    suspend fun execute(name: String) {
        repository.addShopListItem(name = name)
    }
}
