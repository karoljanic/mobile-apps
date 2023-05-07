package com.example.tictactoe.model.utils

sealed class FieldState {
    object EMPTY: FieldState()
    object X: FieldState()
    object O: FieldState()

    fun string(): String {
        return when(this) {
            EMPTY -> ""
            X -> "DARK"
            O -> "LIGHT"
        }
    }
}
