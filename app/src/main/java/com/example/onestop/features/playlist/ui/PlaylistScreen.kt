package com.example.onestop.features.playlist.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onestop.DarkPreview
import com.example.onestop.ui.theme.*

// ── Data ──────────────────────────────────────────────────────────────────────
data class Playlist(val name: String)

// ── Playlist Row ──────────────────────────────────────────────────────────────
@Composable
fun PlayListRow(
    name: String,
    onPlay: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ItemColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            color = TextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            onClick = onPlay,
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(50))
                .background(PlayColor.copy(alpha = 0.15f))
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = PlayColor,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ── Main Screen ───────────────────────────────────────────────────────────────
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaylistScreen(modifier: Modifier = Modifier) {
    val playlists = remember { mutableStateListOf<Playlist>() }
    var url by remember { mutableStateOf("") }
    var urlError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(
                horizontal = 24.dp,
                vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ── Screen title ──────────────────────────────────────────────────
        Text(
            text = "Playlists",
            color = HeadingColor,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 0.5.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // ── URL input card ────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CardColor)
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Add Playlist URL",
                    color = HeadingColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                        urlError = false
                    },
                    placeholder = {
                        Text("https://...", color = TextSub, fontSize = 14.sp)
                    },
                    isError = urlError,
                    supportingText = {
                        if (urlError) Text("Please enter a valid URL", color = ErrorColor, fontSize = 12.sp)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = FieldColor,
                        focusedContainerColor   = FieldColor,
                        unfocusedBorderColor    = StrokeColor,
                        focusedBorderColor      = PlayColor,
                        unfocusedTextColor      = TextPrimary,
                        focusedTextColor        = TextPrimary,
                        cursorColor             = PlayColor,
                        errorBorderColor        = ErrorColor,
                        errorContainerColor     = FieldColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (url.isNotBlank()) {
                            playlists.add(Playlist(url.trim()))
                            url = ""
                        } else {
                            urlError = true
                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor,
                        contentColor   = ButtonTxt
                    ),
                    contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save Playlist", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Playlist list card ────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(CardColor)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Your Playlists",
                    color = HeadingColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${playlists.size} saved",
                    color = TextSub,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                HorizontalDivider(color = DividerColor, thickness = 0.8.dp)
                Spacer(modifier = Modifier.height(12.dp))

                if (playlists.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No playlists yet.\nPaste a URL above to get started.",
                            color = TextSub,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(playlists) { pl ->
                            PlayListRow(
                                name    = pl.name,
                                onPlay  = {},
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}