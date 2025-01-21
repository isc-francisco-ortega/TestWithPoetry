package com.example.testwithpoetry.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoadingScreen

@Serializable
object  EmptyScreen

@Serializable
object UserForm

@Serializable
object AuthorsList

@Serializable
data class ParameterAuthor(
    val nameAuthor: String
)