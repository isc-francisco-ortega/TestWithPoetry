package com.example.testwithpoetry.domain

import com.example.testwithpoetry.domain.localModels.User
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        userRepository.insertUser(user)
    }
}