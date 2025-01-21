package com.example.testwithpoetry.data

import com.example.testwithpoetry.data.room.FavoriteAuthorDao
import com.example.testwithpoetry.data.room.FavoriteAuthorEntity
import com.example.testwithpoetry.domain.FavoritesAuthorsRepository
import javax.inject.Inject


class FavoritesAuthorsRepositoryImpl @Inject constructor(
    private val favoriteAuthorDao: FavoriteAuthorDao
): FavoritesAuthorsRepository {
    override suspend fun getFavoriteAuthors(): List<FavoriteAuthorEntity> {
        return favoriteAuthorDao.getAllFavoriteAuthors()
    }

    override suspend fun insertAuthor(author: FavoriteAuthorEntity) {
        return favoriteAuthorDao.addFavoriteAuthor(author)
    }

    override suspend fun deleteAuthor(author: FavoriteAuthorEntity) {
        return favoriteAuthorDao.removeFavoriteAuthor(author)
    }

    override suspend fun isFavorite(authorName: String): Boolean {
        return favoriteAuthorDao.isFavorite(authorName)
    }
}