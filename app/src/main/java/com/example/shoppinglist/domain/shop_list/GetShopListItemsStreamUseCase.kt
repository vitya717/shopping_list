package com.example.shoppinglist.domain.shop_list

import com.example.shoppinglist.R
import com.example.shoppinglist.data.models.ShopList
import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import com.example.shoppinglist.util.Async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShopListItemsStreamUseCase @Inject constructor(
    private val repository: ShopListRepositoryInterface
) {

    fun execute(): Flow<Async<ShopList>> {
        return repository.getShopListItemsStream()
            .map {
                val all = it.count()
                val bought = it.count { e -> e.isBought }
                val (isBoughtList, isNotBoughtList) = it.partition { e -> e.isBought }
                val list = isNotBoughtList + isBoughtList

                Async.Success(
                    ShopList(
                        items = list,
                        allItemsCount = all,
                        boughtItemsCount = bought
                    )
                )
            }
            .catch<Async<ShopList>> { Async.Error(R.string.shop_list_error) }
    }
}