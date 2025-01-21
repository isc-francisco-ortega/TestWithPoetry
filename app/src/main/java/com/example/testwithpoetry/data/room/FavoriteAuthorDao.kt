package com.example.testwithpoetry.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteAuthorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteAuthor(author: FavoriteAuthorEntity)

    @Delete
    suspend fun removeFavoriteAuthor(author: FavoriteAuthorEntity)

    @Query("SELECT * FROM favorite_authors")
    suspend fun getAllFavoriteAuthors(): List<FavoriteAuthorEntity>

    @Query("SELECT COUNT(*) > 0 FROM favorite_authors WHERE name = :authorName")
    suspend fun isFavorite(authorName: String): Boolean

}