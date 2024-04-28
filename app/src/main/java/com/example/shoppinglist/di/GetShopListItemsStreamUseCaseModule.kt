package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import com.example.shoppinglist.domain.shop_list.GetShopListItemsStreamUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GetShopListItemsStreamUseCaseModule {
    @Singleton
    @Provides
    fun provideGetShopListItemsStreamUseCase(shopListRepository: ShopListRepositoryInterface) : GetShopListItemsStreamUseCase {
        return GetShopListItemsStreamUseCase(shopListRepository)
    }
}
