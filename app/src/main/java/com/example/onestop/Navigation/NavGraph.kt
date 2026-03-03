package com.example.onestop.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.onestop.features.goalTracker.data.GoalViewModel
import com.example.onestop.home.BottomNavBar
import com.example.onestop.home.HomeScreen
import com.example.onestop.features.goalTracker.ui.DailyGoalsScreen
import com.example.onestop.features.playlist.ui.PlaylistScreen
import com.example.onestop.features.studytracker.StudyViewModel
import com.example.onestop.features.studytracker.StudyViewModelFactory
import com.example.onestop.features.studytracker.data.MyDatabase
import com.example.onestop.features.studytracker.data.StudyRepo
import com.example.onestop.features.studytracker.ui.Studyscreen

object Routes {
    const val HOME         = "home"
    const val DAILY_GOALS  = "daily_goals"
    const val STUDY_TRACKER = "study_tracker"
    const val PLAYLIST     = "playlist"
    const val GOAL_TRACKER = "goal_tracker"
}
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val goalViewModel: GoalViewModel = viewModel()

    val context = LocalContext.current.applicationContext
    val db = remember {
        Room.databaseBuilder(context, MyDatabase::class.java, "study-db").build()
    }
    val repo    = remember { StudyRepo(db.studySessionDao()) }
    val factory = remember { StudyViewModelFactory(repo) }
    val studyViewModel: StudyViewModel = viewModel(factory = factory)

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    navController   = navController,
                    goalViewModel   = goalViewModel,
                    studyViewModel  = studyViewModel
                )
            }
            composable(Routes.STUDY_TRACKER) { Studyscreen(viewModel = studyViewModel) }
            composable(Routes.GOAL_TRACKER)  { DailyGoalsScreen(viewModel = goalViewModel) }
            composable(Routes.PLAYLIST)      { PlaylistScreen() }
        }
    }
}