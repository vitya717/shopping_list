package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import com.example.shoppinglist.domain.notes.GetNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GetNoteUseCaseModule {
    @Singleton
    @Provides
    fun provideGetNoteUseCase(notesRepository: NotesRepositoryInterface) : GetNoteUseCase {
        return GetNoteUseCase(notesRepository)
    }
}