package com.example.onestop.features.studytracker.data

class StudyRepo(private val dao: StudySessionDao) {
    suspend fun insert(session: StudySession) {
        dao.insert(session)
    }

    suspend fun deleteSession(session: StudySession) {
        dao.delete(session)
    }

    suspend fun getAllSessions(): List<StudySession> {
        return dao.getAll()
    }

    suspend fun getSessionsByDate(date: String): List<StudySession> =
        dao.getSessionsByDate(date)

    suspend fun getTotalTimeByDate(date: String): Long =
        dao.getTotalTimeByDate(date) ?: 0L
}