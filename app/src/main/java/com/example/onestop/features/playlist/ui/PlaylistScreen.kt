package com.example.onestop.features.playlist.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onestop.ui.theme.OnestopTheme
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

data class Playlist(val name: String)

@Composable
fun PlayListRow(
    name: String,
    onPlay: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(Color(0xFF364252), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistScreen(){
    val playlist= remember { mutableStateListOf<Playlist>() }
    var url by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(
            horizontal = 25.dp,
            vertical=50.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value=url,
            onValueChange = {text -> url=text},
            placeholder = { Text(
                "Enter url of playlist",
                color = Color.Gray,
                fontSize = 18.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF364252),
                focusedContainerColor = Color(0xFF364252)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
        )

        Spacer(modifier=Modifier.height(12.dp))

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
            onClick = {
                if (url.isNotBlank()) {
                    playlist.add(Playlist(url))
                    url = ""
                }
            },
            shape = RoundedCornerShape(20.dp)
        ){
            Text(
                text = "Save playlist",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
                userScrollEnabled = true,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF364252)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stickyHeader {
                    Text(text= "Your Playlists",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                items(playlist) { pl ->
                    PlayListRow(
                        name = pl.name,
                        onPlay = {},
                        onClick = {}
                    )
            }
        }
    }
}

@Preview()
@Composable
fun MyPreviewFunction() {
    OnestopTheme {
        PlaylistScreen()
    }
}