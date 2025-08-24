package com.example.onestop.features.studytracker.data

import androidx.room.*

@Dao
interface StudySessionDao {

    @Insert
    suspend fun insert(item: StudySession)

    @Delete
    suspend fun delete(item: StudySession)

    @Query("SELECT * FROM Study_sessions")
    suspend fun getAll(): List<StudySession>

    @Query("SELECT * FROM Study_sessions WHERE date = :selectedDate")
    suspend fun getSessionsByDate(selectedDate: String): List<StudySession>

    @Query("SELECT SUM(duration) FROM Study_sessions WHERE date = :selectedDate")
    suspend fun getTotalTimeByDate(selectedDate: String): Long?

}