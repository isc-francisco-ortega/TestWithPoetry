package com.example.testwithpoetry.di

import android.content.Context
import androidx.room.Room
import com.example.testwithpoetry.data.room.AppDatabase
import com.example.testwithpoetry.data.room.FavoriteAuthorDao
import com.example.testwithpoetry.data.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideFavoritesAuthorsDao(database: AppDatabase): FavoriteAuthorDao {
        return database.favoritesAuthorsDao()
    }
}