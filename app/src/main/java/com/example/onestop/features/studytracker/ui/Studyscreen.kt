package com.example.onestop.features.studytracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onestop.features.studytracker.StudyViewModel
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.room.Room
import com.example.onestop.DarkPreview
import com.example.onestop.features.studytracker.StudyViewModelFactory
import com.example.onestop.features.studytracker.data.MyDatabase
import com.example.onestop.features.studytracker.data.StudyRepo
import com.example.onestop.features.studytracker.data.StudySession

@Composable
fun SessionItem(session: StudySession) {
    val timeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = "Start: ${timeFormat.format(Date(session.startTime))}",
            color = Color.White,
            fontSize = 16.sp
        )
        Text(
            text = "End: ${timeFormat.format(Date(session.endTime))}",
            color = Color.White,
            fontSize = 16.sp
        )
        Text(
            text = "Duration: ${session.duration} min",
            color = Color(0xFFB0BEC5),
            fontSize = 14.sp
        )
        Divider(
            color = Color.Gray,
            thickness = 0.5.dp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun Studyscreen(modifier: Modifier=Modifier) {
    val context = LocalContext.current.applicationContext

    // Using remember to prevent re-creating DB every recomposition
    val db = remember {
        Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            "study-db"
        ).build()
    }

    val repo = remember { StudyRepo(db.studySessionDao()) }
    val factory = remember { StudyViewModelFactory(repo) }
    val viewModel: StudyViewModel = viewModel(factory = factory)
    //without declaring we cannot call stuff
    var start by remember { mutableStateOf(false) }
    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    val sessions = viewModel.sessions
    val totalTime = viewModel.totalTime
    val selectedDate = viewModel.selectedDate

    LaunchedEffect(viewModel.running) {
        while (viewModel.running) {
            viewModel.updateElapsedTime()
            kotlinx.coroutines.delay(1000)
        }
    }

    fun formatTime(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60)) % 60
        val hours = (ms / (1000 * 60 * 60))
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Text(
            text = formatTime(viewModel.elapsedTime),
            modifier = Modifier.padding(top = 120.dp),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 45.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!viewModel.running) {
            Button(
                onClick = { viewModel.startTime() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        } else {
            Button(
                onClick = {
                    viewModel.endTime()
                    val session = StudySession(
                        startTime = viewModel.start_time,
                        endTime = System.currentTimeMillis(),
                        duration = viewModel.end_time/60000L,
                        date = currentDate
                    )
                    viewModel.saveSession(session)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier.padding(12.dp)
            ) {
                Text(text = "Stop")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF364252))
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)) {

                Text(
                    text = "Session History",
                    fontSize = 24.sp,
                    color = Color(0xFFdde5d0)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Date: ${selectedDate.ifEmpty{currentDate }}",
                    fontSize = 16.sp,
                    color = Color.White
                )

                Text(
                    text = "Total Time: ${totalTime ?: 0} min",
                    fontSize = 16.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(modifier=Modifier.weight(1f)) {
                    items(sessions) { session ->
                        SessionItem(session)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun myPreview(){
    DarkPreview() {
        Studyscreen()
    }
}