package com.example.onestop.features.playlist.data

import androidx.room.*

@Dao
interface PlaylistDao{

    @Insert
    suspend fun insert(item: PlaylistSession)

    @Update
    suspend fun update(item: PlaylistSession)

    @Delete
    suspend fun delete(item: PlaylistSession)

    @Query("SELECT * FROM Playlist_session ORDER BY id DESC")
    suspend fun getAll(): List<PlaylistSession>
}