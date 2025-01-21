package com.example.testwithpoetry.domain

import com.example.testwithpoetry.data.room.FavoriteAuthorEntity
import javax.inject.Inject

class GetFavoritesAuthorsUseCase @Inject constructor(
    private val favoritesAuthorsRepository: FavoritesAuthorsRepository
) {
    suspend operator fun invoke(): List<FavoriteAuthorEntity> {
        return favoritesAuthorsRepository.getFavoriteAuthors()
    }
}