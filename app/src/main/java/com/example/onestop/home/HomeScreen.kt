package com.example.onestop.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onestop.DarkPreview
import com.example.onestop.features.goalTracker.ui.DailyGoalsScreen
import com.example.onestop.home.quranQuote.QuranViewModel
import com.example.onestop.navigation.Routes
import com.example.onestop.ui.theme.*

@Composable
fun OneStopApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) { HomeScreen(navController) }
            composable(Routes.DAILY_GOALS) { DailyGoalsScreen() }
        }
    }
}
// ── Home Screen ───────────────────────────────────────────────────────────────
@Composable
fun HomeScreen(navController: NavController) {
    val quranViewModel: QuranViewModel = viewModel()
    val ayah by quranViewModel.ayah.collectAsState()
    val error by quranViewModel.error.collectAsState()

    // Fetch on first load
    LaunchedEffect(Unit) {
        quranViewModel.fetchRandomAyah()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(
                horizontal = 20.dp,
                vertical = 52.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // App title
        Text(
            text = "OneStop",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ── Stats Row ─────────────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Total Focus",
                value = "1 hr 20 mins"
            )
            StatCard(
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate(Routes.DAILY_GOALS) },
                label = "Daily Goals",
                value = "80%"
            )
        }

        // ── Quran Ayah Card ───────────────────────────────────────────────
        QuranCard(
            arabic = ayah?.first,
            translation = ayah?.second,
            error = error,
            onRefresh = { quranViewModel.fetchRandomAyah() }
        )
    }
}

// ── Quran Card ────────────────────────────────────────────────────────────────
@Composable
fun QuranCard(
    arabic: String?,
    translation: String?,
    error: String?,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardColor)
            .clickable { onRefresh() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Ayah of the moment",
            color = HeadingColor,
            fontSize = 17.sp,
            letterSpacing = 0.5.sp
        )

        when {
            error != null -> {
                Text(
                    text = "Could not load ayah. Tap to retry.",
                    color = TextPrimary,
                    fontSize = 13.sp
                )
            }
            arabic == null -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = TextPrimary, strokeWidth = 2.dp, modifier = Modifier.size(24.dp))
                }
            }
            else -> {
                // Arabic text
                Text(
                    text = arabic,
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                // English translation
                Text(
                    text = translation ?: "",
                    color = HeadingColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                    lineHeight = 18.sp
                )
                // Tap to refresh hint
                Text(
                    text = "Tap for another",
                    color = HeadingColor.copy(alpha = 0.5f),
                    fontSize = 11.sp
                )
            }
        }
    }
}

// ── Stat Card ─────────────────────────────────────────────────────────────────
@Composable
fun StatCard(modifier: Modifier = Modifier, label: String, value: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(CardColor)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            color = HeadingColor,
            fontSize = 17.sp)
        Text(
            text = value,
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Light)
    }
}