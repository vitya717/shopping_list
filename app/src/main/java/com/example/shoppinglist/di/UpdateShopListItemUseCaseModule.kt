package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import com.example.shoppinglist.domain.shop_list.UpdateShopListItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UpdateShopListItemUseCaseModule {
    @Singleton
    @Provides
    fun provideUpdateShopListItemUseCase(repository: ShopListRepositoryInterface) : UpdateShopListItemUseCase {
        return  UpdateShopListItemUseCase(repository)
    }
}

