package com.example.onestop.features.studytracker

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.onestop.features.studytracker.data.StudyRepo
import androidx.lifecycle.viewModelScope
import com.example.onestop.features.studytracker.data.StudySession
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StudyViewModel(
    private val repository: StudyRepo
) : ViewModel() {

    var elapsedTime by mutableStateOf(0L)
        private set

    var running by mutableStateOf(false)
        private set

    var start_time: Long = 0L
        private set

    var end_time by mutableStateOf(0L)
        private set

    var sessions by mutableStateOf<List<StudySession>>(emptyList())
        private set

    var totalTime by mutableStateOf(0L)
        private set

    var selectedDate by mutableStateOf(
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    )

    fun updateElapsedTime() {
        elapsedTime = (System.currentTimeMillis() - start_time)
    }

    fun startTime() {
        running = true
        start_time = System.currentTimeMillis()
        elapsedTime = 0L
    }

    fun endTime() {
        end_time = System.currentTimeMillis() - start_time
        running = false
        elapsedTime = 0L
    }

    fun saveSession(session: StudySession) {
        viewModelScope.launch {
            repository.insert(session)
            loadSessionsByDate(session.date)
        }
    }

    fun loadSessionsByDate(date: String) {
        viewModelScope.launch {
            sessions = repository.getSessionsByDate(date)
            totalTime = repository.getTotalTimeByDate(date)
        }
    }
}