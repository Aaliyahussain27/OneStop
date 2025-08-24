package com.example.onestop.features.playlist.data

class PlaylistRepo(private val dao: PlaylistDao){
    suspend fun insert(session: PlaylistSession){
        dao.insert(session)
    }

    suspend fun deleteSession(session: PlaylistSession){
        dao.delete(session)
    }

    suspend fun update(session: PlaylistSession){
        dao.update(session)
    }

    suspend fun getAllSession():List<PlaylistSession>{
        return dao.getAll()
    }

}