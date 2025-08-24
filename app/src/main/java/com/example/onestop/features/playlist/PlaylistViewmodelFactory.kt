package com.example.onestop.features.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onestop.features.playlist.data.PlaylistRepo

class PlaylistViewModelFactory(
    private val repository: PlaylistRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
