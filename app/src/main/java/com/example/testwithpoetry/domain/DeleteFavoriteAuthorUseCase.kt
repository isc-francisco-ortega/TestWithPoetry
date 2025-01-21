package com.example.testwithpoetry.domain

import com.example.testwithpoetry.data.room.FavoriteAuthorEntity
import javax.inject.Inject

class DeleteFavoriteAuthorUseCase @Inject constructor(
    private val favoritesAuthorsRepository: FavoritesAuthorsRepository
) {
    suspend operator fun invoke(author: FavoriteAuthorEntity): Boolean{
        return try{
            favoritesAuthorsRepository.deleteAuthor(author)
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}