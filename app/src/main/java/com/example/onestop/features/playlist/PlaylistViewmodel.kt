package com.example.onestop.features.playlist

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onestop.features.playlist.data.PlaylistRepo
import com.example.onestop.features.playlist.data.PlaylistSession
import kotlinx.coroutines.launch

class PlaylistViewmodel(
    private val repository: PlaylistRepo
) : ViewModel() {

    var playlist by mutableStateOf<List<PlaylistSession>>(emptyList())
        private set

    fun loadPlaylists() {
        viewModelScope.launch {
            playlist = repository.getAllSession()
        }
    }

    fun addPlaylist(session: PlaylistSession) {
        viewModelScope.launch {
            repository.insert(session)
            loadPlaylists()
        }
    }

    fun deletePlaylist(session: PlaylistSession) {
        viewModelScope.launch {
            repository.deleteSession(session)
            loadPlaylists()
        }
    }

    fun updatePlaylist(session: PlaylistSession) {
        viewModelScope.launch {
            repository.update(session)
            loadPlaylists()
        }
    }

}
