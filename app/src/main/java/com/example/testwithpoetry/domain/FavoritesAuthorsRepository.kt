package com.example.testwithpoetry.domain

import com.example.testwithpoetry.data.room.FavoriteAuthorEntity

interface FavoritesAuthorsRepository {

    suspend fun getFavoriteAuthors():List<FavoriteAuthorEntity>
    suspend fun insertAuthor(author: FavoriteAuthorEntity)
    suspend fun deleteAuthor(author: FavoriteAuthorEntity)
    suspend fun isFavorite(authorName: String): Boolean
}