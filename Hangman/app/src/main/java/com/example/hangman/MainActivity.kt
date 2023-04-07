package com.example.hangman

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var hangmanImage : ImageView
    private lateinit var wordToGuess : TextView
    private lateinit var keyboard : TableLayout
    private lateinit var gameStatus : TextView

    private lateinit var hangmanIds : ArrayList<Int>
    private lateinit var hangmanImages : List<Drawable?>

    private var badTries = 0
    private var chosenWord = ""
    private var currentWordView = ""
    private var gameIsRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hangmanImage = findViewById(R.id.hangman)
        wordToGuess = findViewById(R.id.wordToGuess)
        keyboard = findViewById(R.id.keyboard)
        gameStatus = findViewById(R.id.gameStatus)

        hangmanIds = arrayListOf(R.drawable.hangman0, R.drawable.hangman1,
            R.drawable.hangman2, R.drawable.hangman3, R.drawable.hangman4, R.drawable.hangman5, R.drawable.hangman6)

        hangmanImages = hangmanIds.map { ContextCompat.getDrawable(this, it) }

        initializeGame()
    }

    fun newGame(view: View) { initializeGame() }

    private fun initializeKeyboard() {
        keyboard.removeAllViews()

        for(letters in arrayListOf<String>("QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM")) {
            val keyboardRow = TableRow(this)

            for(letter in letters) {
                val letterButton = Button(this)

                val cellParams = TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
                cellParams.setMargins(3, 3, 3, 3)
                letterButton.layoutParams = cellParams

                letterButton.text = letter.toString()
                letterButton.gravity = Gravity.CENTER
                letterButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                letterButton.setOnClickListener {
                    checkLetter(letter)
                    letterButton.isEnabled = false
                }

                keyboardRow.addView(letterButton)
            }

            val rowParams = TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
            keyboardRow.layoutParams = rowParams
            keyboard.addView(keyboardRow)
        }
    }

    private fun initializeGame() {
        initializeKeyboard()

        hangmanImage.setImageDrawable(hangmanImages[0])
        gameStatus.text = ""

        badTries = 0

        val dictionary: Array<String> = resources.getStringArray(R.array.dictionary)
        chosenWord = dictionary.random()
        currentWordView = ""

        for(chr in chosenWord) {
            if(chr == ' ')
                currentWordView += " "
            else
                currentWordView += "_"
        }

        wordToGuess.text = currentWordView
        gameIsRunning = true
    }

    private fun checkLetter(letter : Char) {
        if(!gameIsRunning)
            return

        if(chosenWord.contains(letter)) {
            for(i in chosenWord.indices) {
                if(chosenWord[i] == letter)
                    currentWordView = currentWordView.substring(0, i) + letter + currentWordView.substring(i + 1)
            }

            wordToGuess.text = currentWordView
            if(currentWordView == chosenWord) {
                gameIsRunning = false
                gameStatus.text = getText(R.string.winner)
                gameStatus.setTextColor(Color.GREEN)
            }
        }
        else {
            badTries++
            hangmanImage.setImageDrawable(hangmanImages[badTries])


            if(badTries == 6) {
                gameIsRunning = false
                gameStatus.text = getText(R.string.looser)
                gameStatus.setTextColor(Color.RED)
                wordToGuess.text = chosenWord
            }
        }
    }
}