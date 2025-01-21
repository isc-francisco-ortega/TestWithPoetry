package com.example.testwithpoetry.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.domain.GetUserUseCase
import com.example.testwithpoetry.domain.localModels.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getuserUseCase: GetUserUseCase
): ViewModel() {
    private val _userExists = mutableStateOf<Boolean?>(null)
    val userExists: State<Boolean?> = _userExists

    init {
        fetchUser()
    }

    fun fetchUser() {
        viewModelScope.launch {
            val user = getuserUseCase()
            _userExists.value = user != null
        }
    }
}