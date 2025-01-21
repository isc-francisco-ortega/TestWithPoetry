package com.example.testwithpoetry.domain

import com.example.testwithpoetry.data.room.FavoriteAuthorEntity
import javax.inject.Inject

class InsertFavoriteAuthorUseCase @Inject constructor(
    private val favoritesAuthorsRepository: FavoritesAuthorsRepository
) {
    suspend operator fun invoke(author: FavoriteAuthorEntity): Boolean{
        return try {
            if (favoritesAuthorsRepository.isFavorite(author.name)) {
                false
            } else {
                favoritesAuthorsRepository.insertAuthor(author)
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}