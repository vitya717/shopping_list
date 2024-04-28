package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import com.example.shoppinglist.domain.shop_list.DeleteShopListItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeleteShopListItemUseCaseModule {
    @Singleton
    @Provides
    fun provideDeleteShopListItemUseCase(repository: ShopListRepositoryInterface) : DeleteShopListItemUseCase {
        return  DeleteShopListItemUseCase(repository)
    }
}