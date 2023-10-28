package com.example.spaceinvaderslight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.example.spaceinvaderslight.model.Player
import com.example.spaceinvaderslight.room.PlayerDatabase
import com.example.spaceinvaderslight.room.PlayerModel
import com.example.spaceinvaderslight.room.PlayerRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    private lateinit var repo: PlayerRepository
    private lateinit var bestScoreText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerDB = PlayerDatabase.getDatabase(application).playerDao()
        repo = PlayerRepository(playerDB)

        val view = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(view)

        bestScoreText = view.findViewById<TextView>(R.id.bestScore)

        view.findViewById<Button>(R.id.createNewGame).setOnClickListener {
            val gameId = view.findViewById<EditText>(R.id.gameId).text.toString()

            if(gameId.isNotEmpty()) {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("GAME_ID", gameId)
                intent.putExtra("ROLE", "HOST")
                startActivity(intent)
            }
        }

        view.findViewById<Button>(R.id.joinGame).setOnClickListener {
            val gameId = view.findViewById<EditText>(R.id.gameId).text.toString()

            if(gameId.isNotEmpty()) {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("GAME_ID", gameId)
                intent.putExtra("ROLE", "GUEST")
                startActivity(intent)
            }
        }


    }

    override fun onResume() {
        super.onResume()

        var players: List<PlayerModel>
        GlobalScope.launch {
            players = repo.getAll()
            runOnUiThread { bestScoreText.text = "Best score: player with ID ${players[0].id} scored ${players[0].score} points" }
        }
    }
}