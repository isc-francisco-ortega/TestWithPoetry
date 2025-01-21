package com.example.testwithpoetry.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_authors")
data class FavoriteAuthorEntity(
    @PrimaryKey val name: String
)