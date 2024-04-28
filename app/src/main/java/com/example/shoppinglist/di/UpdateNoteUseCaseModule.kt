package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import com.example.shoppinglist.domain.notes.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UpdateNoteUseCaseModule {
    @Singleton
    @Provides
    fun provideUpdateNoteUseCase(notesRepository: NotesRepositoryInterface) : UpdateNoteUseCase {
        return UpdateNoteUseCase(notesRepository)
    }
}