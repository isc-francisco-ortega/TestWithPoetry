package com.example.testwithpoetry.domain

import com.example.testwithpoetry.NetworkResource
import com.example.testwithpoetry.remoteResponses.PoemResponse
import javax.inject.Inject

class GetPoemUseCase @Inject constructor(
    private val repository: PoetryRepository
) {
    suspend operator fun invoke(authorName: String, title: String): NetworkResource<List<PoemResponse>>{
        return repository.getPoem(authorName, title)
    }
}