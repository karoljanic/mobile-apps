package com.example.tictactoe.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.GameViewModel
import com.example.tictactoe.ui.theme.Purple500
import com.example.tictactoe.ui.theme.Purple700


@Composable
fun StartNewGame(gameViewModel: GameViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val boardSize = remember { mutableStateOf("") }

    Row(
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = {
                openDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
            border = BorderStroke(1.dp, Purple500)
        ) {
            Text(text = "New Game!", fontSize = 20.sp)
        }
    }

    if (openDialog.value) {
        AlertDialog(onDismissRequest = { }, confirmButton = {
            TextButton(onClick = {
                openDialog.value = false
                try {
                    val n = boardSize.value.toInt()
                    if (n in 3..20) gameViewModel.startNewGame(n)
                } catch (_: NumberFormatException) {
                }
            }) { Text(text = "OK") }
        }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) { Text(text = "Cancel") }
        }, title = {
            Text(
                text = "Input Board Size", fontSize = 15.sp
            )
        }, text = {
            Column {
                TextField(value = boardSize.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { boardSize.value = it })
            }
        })
    }
}