package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.ShopListRepositoryInterface
import com.example.shoppinglist.data.repositories.ShopListsRepository
import com.example.shoppinglist.data.sources.local.database.ShopListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ShopListsRepositoryModule {
    @Singleton
    @Provides
    fun provideShopListsRepository(shopListDao: ShopListDao) : ShopListRepositoryInterface {
        return  ShopListsRepository(shopListDao)
    }

}

