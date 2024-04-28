package com.example.shoppinglist.data.models

import com.example.shoppinglist.data.sources.local.entities.NoteEntity

fun NoteEntity.toExternal() =
    Note(
        id = id,
        title = title,
        description = description,
        lastUpdatedDateTime = lastUpdatedDateTime
    )

//fun Note.toLocal() =
//    NoteEntity(
//        id = id,
//        title = title,
//        description = description,
//    )

@JvmName("localToExternal")
fun List<NoteEntity>.toExternal() = map(NoteEntity::toExternal)

//fun List<Note>.toLocal() = map(Note::toLocal)
