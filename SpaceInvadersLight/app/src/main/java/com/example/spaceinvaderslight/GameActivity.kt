package com.example.spaceinvaderslight

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spaceinvaderslight.firebase.FirebaseDatabaseRepository
import com.example.spaceinvaderslight.view.SpaceInvadersGuestView
import com.example.spaceinvaderslight.view.SpaceInvadersHostView
import com.example.spaceinvaderslight.view.SpaceInvadersView


class GameActivity: AppCompatActivity() {
    private lateinit var spaceInvadersView: SpaceInvadersView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameId = intent.getStringExtra("GAME_ID")!!
        val role = intent.getStringExtra("ROLE")!!

        spaceInvadersView = if(role == "HOST") {
            FirebaseDatabaseRepository.createGame(gameId)
            SpaceInvadersHostView(this, this, gameId)
        }
        else
            SpaceInvadersGuestView(this, this, gameId)

        setContentView(spaceInvadersView)
    }

    override fun onBackPressed() {

    }
}