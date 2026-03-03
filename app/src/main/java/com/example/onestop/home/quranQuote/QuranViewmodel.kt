package com.example.onestop.home.quranQuote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranViewModel : ViewModel() {

    private val repository = QuranRepository(RetrofitInstance.api)

    private val _ayah = MutableStateFlow<Pair<String, String>?>(null)
    val ayah: StateFlow<Pair<String, String>?> = _ayah

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchRandomAyah() {
        viewModelScope.launch {
            val result = repository.getRandomAyah()
            if (result.isSuccess) {
                _ayah.value = result.getOrNull()
            } else {
                _error.value = "Kuch gadbad hui!"
            }
        }
    }
}