package com.example.note_crud

import java.io.Serializable

data class MainModel(
    val notes: List<Note>
)

data class Note(
    val id: String,
    val note: String
): Serializable