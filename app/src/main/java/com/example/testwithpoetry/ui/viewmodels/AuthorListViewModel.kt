package com.example.testwithpoetry.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testwithpoetry.NetworkResource
import com.example.testwithpoetry.data.room.FavoriteAuthorEntity
import com.example.testwithpoetry.data.room.FavoriteAuthorDao
import com.example.testwithpoetry.domain.DeleteFavoriteAuthorUseCase
import com.example.testwithpoetry.domain.GetAuthorsUseCase
import com.example.testwithpoetry.domain.GetFavoritesAuthorsUseCase
import com.example.testwithpoetry.domain.GetUserUseCase
import com.example.testwithpoetry.domain.InsertFavoriteAuthorUseCase
import com.example.testwithpoetry.domain.localModels.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorListViewModel @Inject constructor(
    private val getAuthsUseCase: GetAuthorsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getFavoritesAuthorsUseCase: GetFavoritesAuthorsUseCase,
    private val insertFavoriteAuthorUseCase: InsertFavoriteAuthorUseCase,
    private val deleteFavoriteAuthorUseCase: DeleteFavoriteAuthorUseCase
): ViewModel() {
    private val _authors = MutableStateFlow<List<String>>(emptyList())
    val authors: StateFlow<List<String>> = _authors

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _favoritesAuthors = MutableStateFlow<List<String>>(emptyList())
    val favoritesAuthors: StateFlow<List<String>> = _favoritesAuthors

    init{
        loadUser()
        loadItems()
        loadFavoritesAuthors()
    }

    private fun loadItems() {
        viewModelScope.launch {
            val result = getAuthsUseCase()

            when(result){
                is NetworkResource.Success->{
                    _authors.value = result.data.authors
                }
                is NetworkResource.Fail -> {
                    _errorMessage.value = result.error
                }
            }
        }
    }

    private fun loadUser(){
        viewModelScope.launch {
            val user = getUserUseCase()
            _user.value = user
        }
    }

    fun loadFavoritesAuthors(){
        viewModelScope.launch {
            val favorites = getFavoritesAuthorsUseCase()
            _favoritesAuthors.value = favorites.map { it.name }
        }
    }

    fun insertFavoriteAuthor(author: String, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            val result = insertFavoriteAuthorUseCase(FavoriteAuthorEntity(name = author))
            callback(result)
        }
    }

    fun deleteFavoriteAuthor(author: String, callback: (Boolean) -> Unit){
        viewModelScope.launch {
            val result = deleteFavoriteAuthorUseCase(FavoriteAuthorEntity(name = author))
            callback(result)
        }
    }
}