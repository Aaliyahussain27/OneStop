package com.example.onestop.features.goalTracker.data

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

data class Goal(
    val id: Int,
    val name: String,
    val completed: Boolean = false
)

class GoalViewModel : ViewModel() {
    var goals by mutableStateOf(listOf<Goal>())
        private set

    val total     get() = goals.size
    val completed get() = goals.count { it.completed }

    fun addGoal(name: String) {
        val newId = (goals.maxOfOrNull { it.id } ?: 0) + 1
        goals = goals + Goal(newId, name.trim())
    }

    fun toggleGoal(goal: Goal) {
        goals = goals.map {
            if (it.id == goal.id) it.copy(completed = !it.completed) else it
        }
    }
}