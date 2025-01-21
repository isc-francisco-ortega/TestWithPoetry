package com.example.testwithpoetry.domain

import com.example.testwithpoetry.domain.localModels.User

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun insertUser(user: User)
}