package com.example.testwithpoetry.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.domain.GetUserUseCase
import com.example.testwithpoetry.domain.localModels.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    init {
        getUser()
    }

    private fun getUser(){
        viewModelScope.launch {
            val result = getUserUseCase()
            _user.value = result
            }
    }
}