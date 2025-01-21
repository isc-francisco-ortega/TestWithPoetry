package com.example.testwithpoetry.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, FavoriteAuthorEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoritesAuthorsDao(): FavoriteAuthorDao
}