package com.example.shoppinglist.di

import android.content.Context
import com.example.shoppinglist.data.repositories.NotesRepository
import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import com.example.shoppinglist.data.sources.local.database.AppDatabase
import com.example.shoppinglist.data.sources.local.database.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotesRepositoryModule {
    @Singleton
    @Provides
    fun provideNotesRepository(noteDao: NoteDao) : NotesRepositoryInterface {
        return NotesRepository(noteDao)
    }
}