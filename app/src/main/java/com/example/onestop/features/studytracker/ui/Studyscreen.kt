package com.example.onestop.features.studytracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.onestop.DarkPreview
import com.example.onestop.features.studytracker.StudyViewModel
import com.example.onestop.features.studytracker.StudyViewModelFactory
import com.example.onestop.features.studytracker.data.MyDatabase
import com.example.onestop.features.studytracker.data.StudyRepo
import com.example.onestop.features.studytracker.data.StudySession
import com.example.onestop.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun SessionRow(session: StudySession) {
    val fmt = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(fmt.format(Date(session.startTime)), color = BodyText, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Text(fmt.format(Date(session.endTime)),   color = BodyText, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Text("${session.duration} min",           color = SubText,  fontSize = 13.sp, modifier = Modifier.weight(1f))
    }
    HorizontalDivider(color = DividerColor, thickness = 0.5.dp)
}

// ── Main Screen ───────────────────────────────────────────────────────────────
@Composable
fun Studyscreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current.applicationContext
    val db = remember {
        Room.databaseBuilder(context, MyDatabase::class.java, "study-db").build()
    }
    val repo    = remember { StudyRepo(db.studySessionDao()) }
    val factory = remember { StudyViewModelFactory(repo) }
    val viewModel: StudyViewModel = viewModel(factory = factory)

    val currentDate  = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()) }
    val sessions     = viewModel.sessions
    val totalTime    = viewModel.totalTime
    val selectedDate = viewModel.selectedDate

    // 25 min session max for progress ring
    val maxMs    = 25 * 60 * 1000L
    val progress = (viewModel.elapsedTime.toFloat() / maxMs).coerceIn(0f, 1f)

    LaunchedEffect(viewModel.running) {
        while (viewModel.running) {
            viewModel.updateElapsedTime()
            kotlinx.coroutines.delay(1000)
        }
    }

    fun formatTime(ms: Long): String {
        val s = (ms / 1000) % 60
        val m = (ms / (1000 * 60)) % 60
        val h = ms / (1000 * 60 * 60)
        return String.format("%02d:%02d:%02d", h, m, s)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(
                horizontal = 24.dp,
                vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // ── Inbuilt CircularProgressIndicator with timer text ──────────────
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(200.dp)
        ) {
            // Track layer (always full, acts as background ring)
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.fillMaxSize(),
                color = RingTrack,
                strokeWidth = 10.dp,
                strokeCap = StrokeCap.Round,
            )
            // Progress layer
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxSize(),
                color = RingProgress,
                strokeWidth = 10.dp,
                strokeCap = StrokeCap.Round,
            )
            // Timer text in center
            Text(
                text = formatTime(viewModel.elapsedTime),
                color = TimerText,
                fontSize = 34.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Start / Stop button ───────────────────────────────────────────
        if (!viewModel.running) {
            Button(
                onClick = { viewModel.startTime() },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor   = ButtonTxt
                ),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 10.dp)
            ) {
                Text(
                    "Start",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold)
            }
        } else {
            Button(
                onClick = {
                    viewModel.endTime()
                    viewModel.saveSession(
                        StudySession(
                            startTime = viewModel.start_time,
                            endTime   = System.currentTimeMillis(),
                            duration  = viewModel.end_time / 60000L,
                            date      = currentDate
                        )
                    )
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor   = ButtonTxt
                ),
                contentPadding = PaddingValues(horizontal = 40.dp, vertical = 10.dp)
            ) {
                Text("Stop", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── Session History Card ──────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(CardColor)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Session History",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HeadingColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Date: ${selectedDate.ifEmpty { currentDate }}", fontSize = 13.sp, color = SubText)
                    Text("Total: ${totalTime ?: 0} min",                 fontSize = 13.sp, color = SubText)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Table header
                Row(modifier = Modifier.fillMaxWidth()) {
                    listOf("Start Time", "End Time", "Duration").forEach { header ->
                        Text(
                            text = header,
                            color = SubText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                HorizontalDivider(
                    color = DividerColor,
                    thickness = 0.8.dp,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(sessions) { session ->
                        SessionRow(session)
                    }
                }
            }
        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF1C2130, widthDp = 360, heightDp = 760)
@Composable
fun StudyScreenPreview() {
    DarkPreview {
        Studyscreen()
    }
}