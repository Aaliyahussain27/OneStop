package com.example.onestop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.onestop.features.playlist.ui.PlaylistScreen
import com.example.onestop.features.studytracker.ui.Studyscreen
import com.example.onestop.ui.theme.OnestopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnestopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Studyscreen( modifier = Modifier.padding(innerPadding))
                    //PlaylistScreen()
                }
            }
        }
    }
}
