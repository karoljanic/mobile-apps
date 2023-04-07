package com.example.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    class Cell (var isHide : Boolean, var hasMine : Boolean, var hasFlag : Boolean, var view : TextView) { }

    private val mineGridSize = 9
    private val minesNumber = 10

    private var flagging = false
    private var gameIsRunning = true
    private var hideCellsNumber = 0
    private var turnsNumber = 0
    private lateinit var cellsArray : Array<Array<Cell>>

    private lateinit var mineGrid : TableLayout
    private lateinit var restartButton : Button
    private lateinit var itemButton : Button
    private lateinit var gameResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mineGrid = findViewById(R.id.mineGrid);
        restartButton = findViewById(R.id.restartButton)
        itemButton = findViewById(R.id.itemButton)
        gameResult = findViewById(R.id.gameResult)

        initializeGame()
    }

    private fun initializeGame() {
        gameResult.text = ""
        itemButton.text = getText(R.string.bomb)
        flagging = false
        turnsNumber = 0

        cellsArray = Array(mineGridSize) { Array(mineGridSize) { Cell(isHide = true, hasMine = false, hasFlag = false, view = TextView(this)) } }

        for (i in 0 until mineGridSize) {
            val gridRow = TableRow(this)

            for(j in 0 until 9) {
                val gridCell = Button(this)

                val cellParams = TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
                cellParams.setMargins(3, 3, 3, 3)
                gridCell.layoutParams = cellParams

                gridCell.gravity = Gravity.CENTER
                gridCell.setBackgroundColor(getColor(R.color.baby_blue))
                gridCell.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                gridCell.setTextColor(getColor(R.color.white))
                gridCell.setOnClickListener { clickedOnCell(i, j) }

                cellsArray[i][j] = Cell(isHide = true, hasFlag = false, hasMine = false, view = gridCell)

                gridRow.addView(gridCell)
            }

            val rowParams = TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
            gridRow.layoutParams = rowParams
            mineGrid.addView(gridRow)
        }

        setBombs()
        hideCellsNumber = mineGridSize * mineGridSize;
        gameIsRunning = true;
    }

    fun changeItem(view: View) {
        if(flagging)
            itemButton.text = getText(R.string.bomb)
        else
            itemButton.text = getText(R.string.flag)

        flagging = !flagging
    }

    fun restartGame(view: View) {
        mineGrid.removeAllViews()
        initializeGame()
    }

    private fun setBombs() {
        var i = 0
        var j = 0

        repeat(minesNumber) {
            do {
                i = Random.nextInt(0, mineGridSize)
                j = Random.nextInt(0, mineGridSize)
            }
            while(cellsArray[i][j].hasMine)

            cellsArray[i][j].hasMine = true
        }
    }

    private fun clickedOnCell(row : Int, column : Int) {
        if(!gameIsRunning) {
            return
        }

        if(flagging) {
            if(cellsArray[row][column].isHide) {
                if(cellsArray[row][column].hasFlag) {
                    cellsArray[row][column].view.text = ""
                    cellsArray[row][column].hasFlag = false
                }
                else {
                    cellsArray[row][column].view.text = getText(R.string.flag)
                    cellsArray[row][column].hasFlag = true
                }
            }

            return
        }

        if(turnsNumber == 0) {
            for(i in row - 1 .. row + 1) {
                for(j in column - 1 .. column + 1) {
                    if(hasMine(i, j))
                        replaceMine(row, column, i, j)
                }
            }

            turnsNumber++
        }

        if(cellsArray[row][column].hasMine) {
            cellsArray[row][column].view.text = getText(R.string.bomb)
            gameResult.text = getText(R.string.looser)
            gameIsRunning = false

            openAllCells()
        }
        else {
            openEmptyCells(row, column);

            if(hideCellsNumber == minesNumber) {
                gameResult.text = getText(R.string.winner)
                gameIsRunning = false

                openAllCells()
            }
        }
    }

    private fun replaceMine(clickedRow : Int, clickedColumn : Int, row: Int, column: Int) {
        var i = 0
        var j = 0

        do {
            i = Random.nextInt(0, mineGridSize)
            j = Random.nextInt(0, mineGridSize)
        } while ((i < clickedRow-1 || i > clickedRow+1) && (j < clickedColumn-1 || j > clickedColumn+1) && !cellsArray[i][j].hasMine)

        cellsArray[i][j].hasMine = true
        cellsArray[row][column].hasMine = false

        return
    }

    private fun openEmptyCells(row : Int, column: Int) {
        if(row < 0 || row >= mineGridSize)
            return

        if(column < 0 || column >= mineGridSize)
            return

        if(!cellsArray[row][column].isHide)
            return

        val minesAround = countMinesAround(row, column)

        cellsArray[row][column].view.setBackgroundColor(getColor(R.color.light_blue))
        cellsArray[row][column].isHide = false
        hideCellsNumber--

        if(minesAround == 0) {
            openEmptyCells(row - 1, column)
            openEmptyCells(row + 1, column)
            openEmptyCells(row, column + 1)
            openEmptyCells(row, column - 1)
            openEmptyCells(row - 1, column + 1)
            openEmptyCells(row - 1, column - 1)
            openEmptyCells(row + 1, column + 1)
            openEmptyCells(row + 1, column - 1)
        }
        else {
            cellsArray[row][column].view.text = minesAround.toString()
        }

    }

    private fun openAllCells() {
        for (i in 0 until mineGridSize) {
            for (j in 0 until mineGridSize) {
                if(cellsArray[i][j].hasMine) {
                    cellsArray[i][j].view.setBackgroundColor(getColor(R.color.light_blue))
                    cellsArray[i][j].view.text = getText(R.string.bomb)
                }
            }
        }
    }

    private fun countMinesAround(row : Int, column : Int) : Int {
        var minesNumber = 0
        if(hasMine(row - 1, column))
            minesNumber++

        if(hasMine(row + 1, column))
            minesNumber++

        if(hasMine(row, column + 1))
            minesNumber++

        if(hasMine(row, column - 1))
            minesNumber++

        if(hasMine(row - 1, column + 1))
            minesNumber++

        if(hasMine(row - 1, column - 1))
            minesNumber++

        if(hasMine(row + 1, column + 1))
            minesNumber++

        if(hasMine(row + 1, column - 1))
            minesNumber++


        return minesNumber
    }

    private fun hasMine(row : Int, column : Int) : Boolean {
        if(row < 0 || row >= mineGridSize)
            return false

        if(column < 0 || column >= mineGridSize)
            return false

        return cellsArray[row][column].hasMine
    }
}