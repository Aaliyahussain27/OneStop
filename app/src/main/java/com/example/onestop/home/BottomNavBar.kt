package com.example.onestop.home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.onestop.ui.theme.CardColor

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home         : BottomNavItem("home",          "Home",  Icons.Filled.Home)
    object StudyTracker : BottomNavItem("study_tracker", "Study", Icons.Filled.DateRange)
    object GoalTracker  : BottomNavItem("goal_tracker",  "Goals", Icons.Filled.CheckCircle)
    object Playlist     : BottomNavItem("playlist",      "Music", Icons.Filled.PlayArrow)
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.StudyTracker,
        BottomNavItem.GoalTracker,
        BottomNavItem.Playlist
    )
    val currentRoute by navController.currentBackStackEntryAsState()

    NavigationBar(containerColor = CardColor) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute?.destination?.route == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}