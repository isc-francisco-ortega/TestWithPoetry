package com.example.testwithpoetry.di

import com.example.testwithpoetry.data.FavoritesAuthorsRepositoryImpl
import com.example.testwithpoetry.data.PoetryRepositoryImpl
import com.example.testwithpoetry.data.room.UserDao
import com.example.testwithpoetry.data.UserRepositoryImpl
import com.example.testwithpoetry.data.room.FavoriteAuthorDao
import com.example.testwithpoetry.domain.FavoritesAuthorsRepository
import com.example.testwithpoetry.domain.PoetryRepository
import com.example.testwithpoetry.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun providePoetryRepository(apiClient: HttpClient): PoetryRepository {
        return PoetryRepositoryImpl(apiClient)
    }

    @Provides
    @Singleton
    fun provideFavoritesAuthorsRepository(favoriteAuthorDao: FavoriteAuthorDao): FavoritesAuthorsRepository {
        return FavoritesAuthorsRepositoryImpl(favoriteAuthorDao)
    }
}