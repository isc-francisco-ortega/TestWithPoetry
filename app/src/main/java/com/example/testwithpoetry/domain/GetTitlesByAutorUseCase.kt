package com.example.testwithpoetry.domain

import com.example.testwithpoetry.NetworkResource
import com.example.testwithpoetry.remoteResponses.PoemTitleResponse
import javax.inject.Inject

class GetTitlesByAuthorUseCase @Inject constructor(
    private val poetryRepository: PoetryRepository
) {
    suspend operator fun invoke(authorName: String): NetworkResource<List<PoemTitleResponse>> {
        return poetryRepository.getTitlesByAuthor(authorName)
    }
}