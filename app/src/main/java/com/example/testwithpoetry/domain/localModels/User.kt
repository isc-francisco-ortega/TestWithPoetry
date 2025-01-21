package com.example.testwithpoetry.domain.localModels

data class User (
    val id: Int = 0,
    val name: String,
    val email: String,
    var birthday: Long
)