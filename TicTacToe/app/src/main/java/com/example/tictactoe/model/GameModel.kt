package com.example.tictactoe.model

import androidx.lifecycle.MutableLiveData
import com.example.tictactoe.model.utils.FieldState
import com.example.tictactoe.model.utils.Player

data class GameModel(
    val turnNumber: MutableLiveData<Int> = MutableLiveData(0),
    val boardSize: MutableLiveData<Int> = MutableLiveData(3),
    val boardFields: MutableLiveData<ArrayList<FieldState>> = MutableLiveData(ArrayList(List(9) { FieldState.EMPTY })),
    val isRunning: MutableLiveData<Boolean> = MutableLiveData(false),
    val gameStateDescriptor: MutableLiveData<String> = MutableLiveData(""),
    val currentPlayer: MutableLiveData<Player> = MutableLiveData(Player.PLAYER1)
)
