package com.example.onestop.features.studytracker.data

import androidx.room.*

@Database(entities = [StudySession::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun studySessionDao(): StudySessionDao
}
