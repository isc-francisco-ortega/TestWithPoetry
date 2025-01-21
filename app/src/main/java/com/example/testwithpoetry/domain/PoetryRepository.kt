package com.example.testwithpoetry.domain

import com.example.testwithpoetry.NetworkResource
import com.example.testwithpoetry.remoteResponses.AuthorsResponse
import com.example.testwithpoetry.remoteResponses.PoemResponse
import com.example.testwithpoetry.remoteResponses.PoemTitleResponse

interface PoetryRepository {

    suspend fun getAuths(): NetworkResource<AuthorsResponse>
    suspend fun getTitlesByAuthor(authorName: String): NetworkResource<List<PoemTitleResponse>>
    suspend fun getPoem(authorName: String, title: String): NetworkResource<List<PoemResponse>>

}