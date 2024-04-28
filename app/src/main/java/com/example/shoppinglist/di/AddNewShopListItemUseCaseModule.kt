package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import com.example.shoppinglist.domain.shop_list.AddNewShopListItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AddNewShopListItemUseCaseModule {
    @Singleton
    @Provides
    fun provideAddNewShopListItemUseCase(repository: ShopListRepositoryInterface) : AddNewShopListItemUseCase {
        return  AddNewShopListItemUseCase(repository)
    }
}
