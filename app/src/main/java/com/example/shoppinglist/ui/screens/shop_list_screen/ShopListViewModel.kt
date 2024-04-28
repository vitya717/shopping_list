package com.example.shoppinglist.ui.screens.shop_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.models.ShopListItem
import com.example.shoppinglist.domain.shop_list.AddNewShopListItemUseCase
import com.example.shoppinglist.domain.shop_list.ClearShopListUseCase
import com.example.shoppinglist.domain.shop_list.DeleteShopListItemUseCase
import com.example.shoppinglist.domain.shop_list.GetShopListItemsStreamUseCase
import com.example.shoppinglist.domain.shop_list.UpdateShopListItemUseCase
import com.example.shoppinglist.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShopListUiState(
    val shopList: List<ShopListItem> = emptyList(),
    val allItemsCount: String = "0",
    val boughtItemsCount: String = "0",
    val isNotEmpty: Boolean = true,
    val isLoading: Boolean = false
)

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val addNewShopListItem: AddNewShopListItemUseCase,
    private val updateShopListItem: UpdateShopListItemUseCase,
    private val clearShopList: ClearShopListUseCase,
    private val deleteShopListItem: DeleteShopListItemUseCase,
    getShopListItemsStream: GetShopListItemsStreamUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _shopList = getShopListItemsStream.execute()

    val uaState = combine(_shopList, _isLoading) { shopList, isLoading ->
            when (shopList) {
                Async.Loading -> {
                    ShopListUiState(isLoading = true)
                }

                is Async.Error -> {
                    ShopListUiState(isLoading = isLoading)
                }

                is Async.Success -> {
                    ShopListUiState(
                        shopList = shopList.data.items,
                        allItemsCount = shopList.data.allItemsCount.toString(),
                        boughtItemsCount = shopList.data.boughtItemsCount.toString(),
                        isNotEmpty = shopList.data.items.isNotEmpty(),
                        isLoading = isLoading
                    )
                }
            }

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ShopListUiState()
        )

    fun addShopListItem(name: String) {
        viewModelScope.launch {
            addNewShopListItem.execute(name)
        }
    }

    fun clearShopList() {
        viewModelScope.launch {
            clearShopList.execute()
        }
    }


    fun deleteShopListItem(id: Long?) {
        viewModelScope.launch {
            deleteShopListItem.execute(id)
        }
    }

    private fun updateShopListItem(item: ShopListItem) {
        viewModelScope.launch {
            updateShopListItem.execute(item = item)
        }
    }

    fun updateShopListItem(item: ShopListItem, newName: String, isBought: Boolean) {
        viewModelScope.launch {
            updateShopListItem.execute(
                ShopListItem(
                    id = item.id,
                    name = newName,
                    isBought = isBought
                )
            )
        }
    }


    fun updateShopListItemIsBought(item: ShopListItem, isBought: Boolean) {
        if (item.isBought != isBought) {
            updateShopListItem(
                ShopListItem(
                    id = item.id,
                    name = item.name,
                    // Update this parameter to opposite
                    isBought = !item.isBought
                )
            )
        }
    }
}