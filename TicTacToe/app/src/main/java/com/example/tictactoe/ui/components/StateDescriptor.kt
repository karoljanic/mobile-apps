package com.example.tictactoe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.example.tictactoe.GameViewModel

@Composable
fun StateDescriptor(gameViewModel: GameViewModel) {
    val description by gameViewModel.gameStateDescriptor.observeAsState("")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = description, fontSize = 20.sp)
    }
}