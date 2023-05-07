package com.example.tictactoe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tictactoe.GameViewModel
import com.example.tictactoe.ui.components.Board
import com.example.tictactoe.ui.components.StartNewGame
import com.example.tictactoe.ui.components.StateDescriptor

@Composable
fun GameView(gameViewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        StateDescriptor(gameViewModel)
        Board(gameViewModel)
        StartNewGame(gameViewModel)
    }
}