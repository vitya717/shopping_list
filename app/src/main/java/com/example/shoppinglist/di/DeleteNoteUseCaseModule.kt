package com.example.shoppinglist.di

import com.example.shoppinglist.data.repositories.NotesRepositoryInterface
import com.example.shoppinglist.domain.notes.DeleteNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeleteNoteUseCaseModule {
    @Singleton
    @Provides
    fun provideDeleteNoteUseCase(notesRepository: NotesRepositoryInterface) : DeleteNoteUseCase {
        return DeleteNoteUseCase(notesRepository)
    }
}

