package com.example.testwithpoetry.domain

import com.example.testwithpoetry.NetworkResource
import com.example.testwithpoetry.data.PoetryRepositoryImpl
import com.example.testwithpoetry.remoteResponses.AuthorsResponse
import javax.inject.Inject

class GetAuthorsUseCase @Inject constructor(
    private val poetryRepository: PoetryRepositoryImpl
) {
    suspend operator fun invoke(): NetworkResource<AuthorsResponse> {
        return poetryRepository.getAuths()
    }
}