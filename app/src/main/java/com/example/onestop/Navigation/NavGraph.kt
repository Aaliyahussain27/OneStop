package com.example.onestop.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onestop.home.BottomNavBar
import com.example.onestop.home.HomeScreen
import com.example.onestop.features.goalTracker.ui.DailyGoalsScreen

object Routes {
    const val HOME         = "home"
    const val DAILY_GOALS  = "daily_goals"
    const val STUDY_TRACKER = "study_tracker"
    const val PLAYLIST     = "playlist"
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME)          { HomeScreen(navController) }
            composable(Routes.DAILY_GOALS)   { DailyGoalsScreen() }
            composable(Routes.STUDY_TRACKER) { /* baad mein */ }
            composable(Routes.PLAYLIST)      { /* baad mein */ }
        }
    }
}