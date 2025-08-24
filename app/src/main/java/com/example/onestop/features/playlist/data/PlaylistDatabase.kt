package com.example.onestop.features.playlist.data

import androidx.room.*

@Database(entities = [PlaylistSession::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun playlistdao(): PlaylistDao
}
