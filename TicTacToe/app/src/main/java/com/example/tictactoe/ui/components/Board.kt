package com.example.tictactoe.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.tictactoe.GameViewModel

import com.example.tictactoe.ui.theme.Purple700
import com.example.tictactoe.ui.theme.Teal200
import com.example.tictactoe.model.utils.FieldState


@Composable
fun Board(gameViewModel: GameViewModel) {
    val padding = 3

    val boardSize by gameViewModel.boardSize.observeAsState(3)

    val boardDimension = kotlin.math.min(
        LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp
    )
    val buttonDimension = (boardDimension - 2 * boardSize * padding) / boardSize

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until boardSize) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (j in 0 until boardSize) {
                    BoardField(
                        size = buttonDimension,
                        padding = padding,
                        gameViewModel = gameViewModel,
                        index = i * boardSize + j
                    ) {
                        gameViewModel.makeMove(i * boardSize + j)
                    }
                }
            }
        }
    }
}

@Composable
fun BoardField(
    gameViewModel: GameViewModel, index: Int, size: Int, padding: Int, onclick: () -> Unit
) {
    var buttonColor by remember { mutableStateOf(Color.LightGray) }
    val turn by gameViewModel.turnNumber.observeAsState(0)

    LaunchedEffect(turn) {
        val fieldState = gameViewModel.boardFields.value!![index]
        buttonColor = when (fieldState) {
            FieldState.EMPTY -> Color.LightGray
            FieldState.X -> Purple700
            FieldState.O -> Teal200
        }
    }

    Box(
        modifier = Modifier
            .padding(padding.dp)
            .width(size.dp)
            .height(size.dp)
    ) {
        Button(
            modifier = Modifier
                .width(size.dp)
                .height(size.dp),
            shape = MaterialTheme.shapes.medium,
            onClick = onclick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor
            )
        ) { }
    }
}
