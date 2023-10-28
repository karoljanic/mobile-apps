package com.example.spaceinvaderslight.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import com.example.spaceinvaderslight.Constants
import com.example.spaceinvaderslight.firebase.DatabaseKeys
import com.example.spaceinvaderslight.firebase.FirebaseDatabaseRepository
import com.example.spaceinvaderslight.model.Bullet
import com.example.spaceinvaderslight.model.Invader
import com.example.spaceinvaderslight.model.Player
import com.example.spaceinvaderslight.model.SkinFactory

class SpaceInvadersHostView(private val activity: Activity, context: Context, gameId: String) :
    SpaceInvadersView(context, gameId) {
    override fun initializeGame() {
        playerRole = DatabaseKeys.HOST
        opponentRole = DatabaseKeys.GUEST

        player1 = Player(
            (screenHeight * Constants.PLAYER_HEIGHT).toInt(),
            (screenWidth * Constants.PLAYER_WIDTH).toInt(),
            Constants.PLAYER_SPEED,
            Constants.PLAYER_LEFT_INITIAL_X,
            Constants.PLAYER_LEFT_INITIAL_Y,
            screenWidth
        )

        player1Bullet = Bullet(
            Constants.BULLET_SPEED
        )

        player2 = Player(
            (screenHeight * Constants.PLAYER_HEIGHT).toInt(),
            (screenWidth * Constants.PLAYER_WIDTH).toInt(),
            Constants.PLAYER_SPEED,
            Constants.PLAYER_RIGHT_INITIAL_X,
            Constants.PLAYER_RIGHT_INITIAL_Y,
            screenWidth
        )

        player2Bullet = Bullet(
            Constants.BULLET_SPEED
        )

        player1Skin = SkinFactory.getPlayer1Skin(context, player1!!.width, player1!!.height)
        player2Skin = SkinFactory.getPlayer2Skin(context, player2!!.width, player2!!.height)
        player1BulletColor = Paint().apply { color = Color.parseColor("#0804fc") }
        player2BulletColor = Paint().apply { color = Color.parseColor("#088404") }

        for (i in 0 until Constants.INVADERS_COLUMNS) {
            for (j in 0 until Constants.INVADERS_ROWS) {
                invaders.add(
                    Invader(
                        Constants.INVADER_SPEED,
                        Constants.INVADERS_WIDTH_SPACER * (i + 1) + Constants.INVADER_WIDTH * i,
                        2.0f * Constants.TEXT_SIZE + Constants.INVADERS_HEIGHT_SPACER * (j + 1) + Constants.INVADER_WIDTH * j
                    )
                )

                invadersBullets.add(Bullet(Constants.BULLET_SPEED / 2.0f))
            }
        }

        FirebaseDatabaseRepository.updateInvaderArray(gameId, invaders)
        FirebaseDatabaseRepository.updateInvaderBulletsArray(gameId, invadersBullets)

        FirebaseDatabaseRepository.onPlayerVisibleUpdate(gameId, playerRole) {
            player1!!.visible = it
        }

        FirebaseDatabaseRepository.onPlayerVisibleUpdate(gameId, opponentRole) {
            player2!!.visible = it
        }

        FirebaseDatabaseRepository.onPlayerXPositionUpdate(gameId, opponentRole) {
            player2!!.x = it
        }

        FirebaseDatabaseRepository.onPlayerBulletXUpdate(gameId, opponentRole) { x ->
            player2Bullet!!.x = x
        }

        FirebaseDatabaseRepository.onPlayerBulletYUpdate(gameId, opponentRole) { y ->
            player2Bullet!!.y = y
        }

        FirebaseDatabaseRepository.onPlayerBulletVisibilityUpdate(gameId, opponentRole) { v ->
            player2Bullet!!.isActive = v
        }

        FirebaseDatabaseRepository.onGameEnd(gameId) {
            activity.finish()
        }
    }
}