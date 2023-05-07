package com.example.tictactoe.model.utils

sealed class Player {
    object PLAYER1: Player()
    object PLAYER2: Player()

    fun opponent(): Player {
        return when(this) {
            PLAYER1 -> PLAYER2
            PLAYER2 -> PLAYER1
        }
    }

    fun marker(): FieldState {
        return when(this) {
            PLAYER1 -> FieldState.X
            PLAYER2 -> FieldState.O
        }
    }
}
