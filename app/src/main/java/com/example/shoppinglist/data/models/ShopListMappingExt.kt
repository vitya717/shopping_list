package com.example.shoppinglist.data.models

import com.example.shoppinglist.data.sources.local.entities.NoteEntity
import com.example.shoppinglist.data.sources.local.entities.ShopListItemEntity

fun ShopListItemEntity.toExternal() =
    ShopListItem(
        id = id,
        name = name,
        isBought = isBought
    )

@JvmName("localToExternal")
fun List<ShopListItemEntity>.toExternal() = map(ShopListItemEntity::toExternal)

fun ShopListItem.toLocal() =
    ShopListItemEntity(
        id = id,
        name = name,
        isBought = isBought
    )

