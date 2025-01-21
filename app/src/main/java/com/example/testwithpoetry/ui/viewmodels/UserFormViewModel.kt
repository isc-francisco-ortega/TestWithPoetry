package com.example.testwithpoetry.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.domain.SaveUserUseCase
import com.example.testwithpoetry.domain.localModels.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFormViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {
    private val _user = mutableStateOf(User(name = "", email = "", birthday = 0))
    val user: State<User> get() = _user

    fun onUserChanged(newUser: User) {
        _user.value = newUser
    }

    fun onNameChanged(newName: String) {
        _user.value = _user.value.copy(name = newName)
    }

    fun onEmailChanged(newEmail: String) {
        _user.value = _user.value.copy(email = newEmail)
    }

    fun onBirthdayChanged(newBirthday: Long) {
        _user.value = _user.value.copy(birthday = newBirthday)
    }

    fun saveUser() {
        viewModelScope.launch {
            saveUserUseCase(_user.value)
        }
    }
}