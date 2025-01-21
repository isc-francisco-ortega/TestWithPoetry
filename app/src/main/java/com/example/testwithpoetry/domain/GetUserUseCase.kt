package com.example.testwithpoetry.domain

import com.example.testwithpoetry.domain.localModels.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUser()
    }
}