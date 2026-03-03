package com.example.onestop.features.goalTracker.ui

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.onestop.features.goalTracker.data.Goal
import com.example.onestop.features.goalTracker.data.GoalViewModel
import com.example.onestop.ui.theme.*

@Composable
fun DailyGoalsScreen(viewModel: GoalViewModel = viewModel()) {
    val goals     = viewModel.goals
    val total     = viewModel.total
    val completed = viewModel.completed

    var showDialog   by remember { mutableStateOf(false) }
    var newGoalName  by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false; newGoalName = "" },
            containerColor = CardColor,
            title = {
                Text(
                    "Add Goal",
                    style = MaterialTheme.typography.titleLarge,
                    color = HeadingColor
                )
            },
            text = {
                OutlinedTextField(
                    value = newGoalName,
                    onValueChange = { newGoalName = it },
                    placeholder = { Text("Enter goal name", color = TextSub) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = FieldColor,
                        focusedContainerColor   = FieldColor,
                        unfocusedBorderColor    = StrokeColor,
                        focusedBorderColor      = PlayColor,
                        unfocusedTextColor      = TextPrimary,
                        focusedTextColor        = TextPrimary,
                        cursorColor             = PlayColor,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newGoalName.isNotBlank()) {
                            viewModel.addGoal(newGoalName)
                            newGoalName = ""
                            showDialog = false
                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor, contentColor = ButtonTxt)
                ) {
                    Text("Add", style = MaterialTheme.typography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false; newGoalName = "" }) {
                    Text(
                        "Cancel",
                        color = TextSub,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .padding(
                horizontal = 20.dp,
                vertical = 52.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Daily goals", color = HeadingColor, style = MaterialTheme.typography.headlineMedium)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CardColor)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Goals completed",
                    color = HeadingColor,
                    style = MaterialTheme.typography.bodyLarge)
                Text("$completed/$total", color = HeadingColor, style = MaterialTheme.typography.headlineMedium)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(50))
                        .background(ProgressTrack)
                ) {
                    val progress = if (total == 0) 0f else completed.toFloat() / total
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = progress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(50))
                            .background(Brush.horizontalGradient(colors = listOf(ProgressStart, ProgressEnd)))
                    )
                }
            }
        }

        Text(
            "Task List",
            color = TextPrimary,
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(goals, key = { it.id }) { goal ->
                GoalItem(goal = goal, onToggle = { viewModel.toggleGoal(it) })
            }
        }

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor, contentColor = ButtonTxt)
        ) {
            Text("Add Goal",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun GoalItem(goal: Goal, onToggle: (Goal) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardColor)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            checked = goal.completed,
            onCheckedChange = { onToggle(goal) },
            colors = CheckboxDefaults.colors(
                uncheckedColor = CheckboxColor,
                checkedColor   = CheckedColor,
                checkmarkColor = TextPrimary,
                disabledCheckedColor   = CheckboxColor,
                disabledUncheckedColor = CheckboxColor
            )
        )
        Text(
            text = goal.name,
            color = if (goal.completed) TextSub else TextPrimary,
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (goal.completed) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}