package com.example.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.model.GameModel
import com.example.tictactoe.model.utils.FieldState
import com.example.tictactoe.model.utils.Player


class GameViewModel(initialGameModel: GameModel = GameModel()) : ViewModel() {
    private var _gameModel = initialGameModel

    val turnNumber: LiveData<Int> = _gameModel.turnNumber
    val boardSize: LiveData<Int> = _gameModel.boardSize
    val boardFields: LiveData<ArrayList<FieldState>> = _gameModel.boardFields
    val gameStateDescriptor: LiveData<String> = _gameModel.gameStateDescriptor

    private val currentPlayer: LiveData<Player> = _gameModel.currentPlayer
    private val isRunning: LiveData<Boolean> = _gameModel.isRunning

    init {
        startNewGame(3)
    }

    fun startNewGame(boardSize: Int) {
        _gameModel.boardFields.value = ArrayList(List(boardSize * boardSize) { FieldState.EMPTY })
        _gameModel.boardSize.value = boardSize
        _gameModel.turnNumber.value = 0
        _gameModel.isRunning.value = true
        _gameModel.currentPlayer.value = Player.PLAYER1
        _gameModel.gameStateDescriptor.value =
            "PLAYER ${currentPlayer.value!!.marker().string()}'S MOVE"

    }

    fun makeMove(fieldIndex: Int) {
        if (isRunning.value == null || boardFields.value == null || currentPlayer.value == null) return

        if (!isRunning.value!!) return

        val currentStates = boardFields.value!!
        if (currentStates[fieldIndex] != FieldState.EMPTY) return

        currentStates[fieldIndex] = currentPlayer.value!!.marker()
        _gameModel.boardFields.value = currentStates

        if (playerWon(currentStates, currentPlayer.value!!.marker())) {
            _gameModel.gameStateDescriptor.value =
                "GAME WON ${currentPlayer.value!!.marker().string()}!"
            _gameModel.isRunning.value = false
        }
        else if (isDraw()) {
            _gameModel.gameStateDescriptor.value = "DRAW!"
            _gameModel.isRunning.value = false
        } else {
            _gameModel.gameStateDescriptor.value =
                "PLAYER ${currentPlayer.value!!.opponent().marker().string()}'S MOVE"
        }

        _gameModel.currentPlayer.value = _gameModel.currentPlayer.value!!.opponent()
        _gameModel.turnNumber.value = (_gameModel.turnNumber.value ?: 0) + 1
    }

    private fun isDraw(): Boolean {
        return turnNumber.value!! == (boardSize.value!! * boardSize.value!! - 1)
    }

    private fun playerWon(states: ArrayList<FieldState>, state: FieldState): Boolean {
        val n: Int = boardSize.value!!

        // horizontal lines
        for(row in 0 until n) {
            var allLine = true
            for(column in 0 until n) {
                if(states[row * n + column] != state) {
                    allLine = false
                    break
                }
            }

            if(allLine)
                return true
        }

        // vertical lines
        for(column in 0 until n) {
            var allLine = true
            for(row in 0 until n) {
                if(states[row * n + column] != state) {
                    allLine = false
                    break
                }
            }

            if(allLine)
                return true
        }

        // diagonal 1 line
        var allLine = true
        for(column in 0 until n) {
            val row = column
            if(states[row * n + column] != state) {
                allLine = false
                break
            }
        }

        if(allLine)
            return true

        // diagonal 2 line
        allLine = true
        for(column in 0 until n) {
            val row = n - column - 1
            if(states[row * n + column] != state) {
                allLine = false
                break
            }
        }

        if(allLine)
            return true

        return false
    }
}