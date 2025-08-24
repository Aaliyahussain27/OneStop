package com.example.onestop.features.playlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Playlist_session")
data class PlaylistSession(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val url:String
)
