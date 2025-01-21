package com.example.testwithpoetry.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testwithpoetry.NetworkResource
import com.example.testwithpoetry.domain.GetPoemUseCase
import com.example.testwithpoetry.domain.GetTitlesByAuthorUseCase
import com.example.testwithpoetry.remoteResponses.PoemResponse
import com.example.testwithpoetry.remoteResponses.PoemTitleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorDetailsViewModel @Inject constructor(
    private val getTitlesByAuthorUseCase: GetTitlesByAuthorUseCase,
    private val getPoemUseCase: GetPoemUseCase
): ViewModel(){
    private val _authorDetails = MutableStateFlow<List<PoemTitleResponse>>(emptyList())
    val authorDetails: StateFlow<List<PoemTitleResponse>> = _authorDetails

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _poemDetails = MutableStateFlow<PoemResponse?>(null)
    val poemDetails: StateFlow<PoemResponse?> get() = _poemDetails

    private val _isDialogOpen = mutableStateOf(false)
    val isDialogOpen: State<Boolean> = _isDialogOpen

    fun getAuthorDetails(authorName: String) {
        viewModelScope.launch {
            val result = getTitlesByAuthorUseCase(authorName)
            when(result){
                is NetworkResource.Success->{
                    _authorDetails.value = result.data
                    _errorMessage.value = null
                }
                is NetworkResource.Fail -> {
                    _errorMessage.value = result.error
                }
            }
        }

    }

    fun getPoem(authorName: String, title: String){
        viewModelScope.launch {
            _loading.value = true
            val result = getPoemUseCase(authorName, title)
            try {
                when(result){
                    is NetworkResource.Success->{
                        _poemDetails.value = result.data.first()
                        openDialog()
                    }
                    is NetworkResource.Fail -> {
                        _poemDetails.value =  null
                    }
                }
            } catch (e: Exception) {
                _poemDetails.value = null
            } finally {
                _loading.value = false
            }
        }
    }
    fun getPoemString(listLines:List<String>): String{
        var poem =""
        listLines.forEach{line->
            run {
                if (line.isEmpty()) {
                    poem = "$poem \n"
                }else {
                    poem = "$poem $line"
                }
            }
        }
        return poem
    }

    fun openDialog() {
        _isDialogOpen.value = true
    }

    fun closeDialog() {
        _isDialogOpen.value = false
    }
}