package com.example.spaceinvaderslight.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.spaceinvaderslight.Constants
import com.example.spaceinvaderslight.firebase.DatabaseKeys
import com.example.spaceinvaderslight.firebase.FirebaseDatabaseRepository
import com.example.spaceinvaderslight.model.Bullet
import com.example.spaceinvaderslight.model.Invader
import com.example.spaceinvaderslight.model.Player
import com.example.spaceinvaderslight.model.SkinFactory
import com.example.spaceinvaderslight.room.PlayerDao
import com.example.spaceinvaderslight.room.PlayerDatabase
import com.example.spaceinvaderslight.room.PlayerModel
import com.example.spaceinvaderslight.room.PlayerRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


abstract class SpaceInvadersView(private val context: Context, protected val gameId: String) :
    SurfaceView(context), SurfaceHolder.Callback {
    private var gameThread = SpaceInvadersThread(this)
    protected var screenWidth: Int = 0
    protected var screenHeight: Int = 0

    private var initialized = false

    protected var playerRole = ""
    protected var opponentRole = ""

    protected var player1: Player? = null
    protected var player2: Player? = null
    protected var player1Bullet: Bullet? = null
    protected var player2Bullet: Bullet? = null
    protected var invaders = ArrayList<Invader>()
    protected var invadersBullets = ArrayList<Bullet>()

    private var score = 0

    protected var player1Skin: Bitmap? = null
    protected var player2Skin: Bitmap? = null
    protected var player1BulletColor: Paint? = null
    protected var player2BulletColor: Paint? = null
    private var invaderBulletColor: Paint = Paint().apply { color = Color.MAGENTA }
    private var textColor: Paint = Paint().apply { color = Color.BLACK }

    private var repo: PlayerRepository

    init {
        holder.addCallback(this)

        val playerDB = PlayerDatabase.getDatabase(context).playerDao()
        repo = PlayerRepository(playerDB)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val canvas = holder.lockCanvas()
        screenWidth = canvas.width
        screenHeight = canvas.height
        holder.unlockCanvasAndPost(canvas)

        if (!initialized) {
            initializeGame()

            player1!!.visible = true
            FirebaseDatabaseRepository.enablePlayer(gameId, playerRole)

            draw()
            initialized = true
        }

        gameThread = SpaceInvadersThread(this)
        gameThread.running = true
        gameThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameThread.running = false

        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            Log.e("SpaceInvadersLog", "SpaceInvadersThread Join Error!")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val motionArea = 0.7f * screenHeight
        val eventAction = event?.action?.and(MotionEvent.ACTION_MASK)

        if (eventAction == MotionEvent.ACTION_DOWN) {
            if (event.y > motionArea) {
                if (event.x > screenWidth / 2) player1!!.setMovementDirection(Player.MoveDirection.RIGHT)
                else player1!!.setMovementDirection(Player.MoveDirection.LEFT)
            } else if (event.y < motionArea) {
                player1Bullet!!.shoot(
                    player1!!.x + Constants.PLAYER_WIDTH / 2, player1!!.y, Bullet.MoveDirection.UP
                )
                FirebaseDatabaseRepository.setPlayerBulletVisibility(
                    gameId, playerRole, player1Bullet!!.isActive
                )
            }
        }

        return super.onTouchEvent(event)
    }

    fun draw() {
        val canvas = holder.lockCanvas() ?: return
        screenWidth = canvas.width
        screenHeight = canvas.height

        canvas.drawColor(Color.WHITE)
        textColor.apply { textSize = canvas.height * Constants.TEXT_SIZE }

        val text = "Score: $score   Lives: ${player1!!.lives}"
        canvas.drawText(
            text,
            (canvas.width - textColor.measureText(text)) / 2,
            1.5f * Constants.TEXT_SIZE * canvas.height,
            textColor
        )

        if (player1!!.visible) {
            canvas.drawBitmap(
                player1Skin!!, player1!!.x * canvas.width, player1!!.y * canvas.height, null
            )
        }

        if (player2!!.visible) {
            canvas.drawBitmap(
                player2Skin!!, player2!!.x * canvas.width, player2!!.y * canvas.height, null
            )
        }

        for ((counter, invader) in invaders.withIndex()) {
            if (!invader.isVisible) continue

            if (counter % 2 == 0) {
                canvas.drawBitmap(
                    SkinFactory.getInvader1Skin(
                        context,
                        (Constants.INVADER_WIDTH * canvas.width).toInt(),
                        (Constants.INVADER_HEIGHT * canvas.height).toInt()
                    ),
                    invader.x * canvas.width,
                    invader.y * canvas.height,
                    null
                )
            } else {
                canvas.drawBitmap(
                    SkinFactory.getInvader2Skin(
                        context,
                        (Constants.INVADER_WIDTH * canvas.width).toInt(),
                        (Constants.INVADER_HEIGHT * canvas.height).toInt()
                    ), invader.x * canvas.width, invader.y * canvas.height, null
                )
            }

        }

        if (player1Bullet!!.isActive) {
            canvas.drawRect(RectF().apply {
                left = (player1Bullet!!.x - Constants.BULLET_WIDTH / 2) * canvas.width
                right = (player1Bullet!!.x + Constants.BULLET_WIDTH) * canvas.width
                top = player1Bullet!!.y * canvas.height
                bottom = (player1Bullet!!.y + Constants.BULLET_HEIGHT) * canvas.height
            }, player1BulletColor!!)
        }

        if (player2Bullet!!.isActive) {
            canvas.drawRect(RectF().apply {
                left = (player2Bullet!!.x - Constants.BULLET_WIDTH / 2) * canvas.width
                right = (player2Bullet!!.x + Constants.BULLET_WIDTH) * canvas.width
                top = player2Bullet!!.y * canvas.height
                bottom = (player2Bullet!!.y + Constants.BULLET_HEIGHT) * canvas.height
            }, player2BulletColor!!)
        }

        for (bullet in invadersBullets) {
            if (bullet.isActive) {
                canvas.drawRect(RectF().apply {
                    left = (bullet.x - Constants.BULLET_WIDTH / 2) * canvas.width
                    right = (bullet.x + Constants.BULLET_WIDTH) * canvas.width
                    top = bullet.y * canvas.height
                    bottom = (bullet.y + Constants.BULLET_HEIGHT) * canvas.height
                }, invaderBulletColor)
            }
        }

        holder.unlockCanvasAndPost(canvas)
    }

    abstract fun initializeGame()

    fun update(fps: Long) {
        player1!!.update(fps)
        FirebaseDatabaseRepository.movePlayer(gameId, playerRole, player1!!.x, player1!!.y)

        player1Bullet!!.update(fps)
        if (player1Bullet!!.y < 0) {
            player1Bullet!!.isActive = false
            FirebaseDatabaseRepository.setPlayerBulletVisibility(gameId, playerRole, false)
        }

        FirebaseDatabaseRepository.movePlayerBullet(
            gameId,
            playerRole,
            player1Bullet!!.x,
            player1Bullet!!.y
        )

        if(playerRole == DatabaseKeys.HOST) {
            updateInvadersPosition(fps)
        }
    }

    private fun updateInvadersPosition(fps: Long) {
        var bumped = false

        for ((counter, invader) in invaders.withIndex()) {
            if (!invader.isVisible)
                continue

            invader.update(fps)
            val p1 = player1!!.visible && invader.takeAim(player1!!.x, Constants.PLAYER_WIDTH)
            val p2 = player2!!.visible && invader.takeAim(player2!!.x, Constants.PLAYER_WIDTH)

            if (p1 || p2) {
                invadersBullets[counter].shoot(
                    invader.x + Constants.INVADER_WIDTH / 2,
                    invader.y,
                    Bullet.MoveDirection.DOWN
                )
            }

            if (invader.x + Constants.INVADER_WIDTH > 1 || invader.x < 0)
                bumped = true
        }

        if (bumped) {
            for (invader in invaders) {
                invader.dropDownAndReverse(Constants.INVADER_HEIGHT / 4)
                if (invader.y > Constants.INVADER_MIN_LEVEL) {
                    player1!!.lives = 0
                    player2!!.lives = 0
                    finishAndSave()
                }
            }
        }

        for(invaderBullet in invadersBullets) {
            invaderBullet.update(fps)
            if(invaderBullet.y > 1.0f) {
                invaderBullet.isActive = false
            }
        }
        
        for(player in arrayListOf(player1!!, player2!!)) {
            val playerRect = RectF().apply {
                left = player.x
                right = player.x + Constants.PLAYER_WIDTH
                top = player.y
                bottom = player.y + Constants.PLAYER_HEIGHT
            }
            for(invaderBullet in invadersBullets) {
                if(!invaderBullet.isActive) {
                    continue
                }

                val bulletRect = RectF().apply {
                    left = invaderBullet.x
                    right = invaderBullet.x + Constants.BULLET_WIDTH
                    top = invaderBullet.y
                    bottom = invaderBullet.y + Constants.BULLET_HEIGHT
                }

                if(RectF.intersects(playerRect, bulletRect)) {
                    invaderBullet.isActive = false
                    player.lives--
                    if(player1!!.lives <= 0) {
                        player1!!.visible = false
                        FirebaseDatabaseRepository.disablePlayer(gameId, playerRole)
                        finishAndSave()
                    }
                    if(player2!!.lives <= 0) {
                        player2!!.visible = false
                        FirebaseDatabaseRepository.disablePlayer(gameId, opponentRole)
                        finishAndSave()
                    }

                    FirebaseDatabaseRepository.updateLives(gameId, playerRole, player1!!.lives)
                    FirebaseDatabaseRepository.updateLives(gameId, opponentRole, player2!!.lives)
                }
            }
        }

        for(playerBullet in arrayListOf(player1Bullet!!, player2Bullet!!)) {
            if (playerBullet.isActive) {
                val bulletRect = RectF().apply {
                    left = playerBullet.x
                    right = playerBullet.x + Constants.BULLET_WIDTH
                    top = playerBullet.y
                    bottom = playerBullet.y + Constants.BULLET_HEIGHT
                }

                for (invader in invaders) {
                    if (!invader.isVisible)
                        continue

                    val invaderRect = RectF().apply {
                        left = invader.x
                        right = invader.x + Constants.INVADER_WIDTH
                        top = invader.y
                        bottom = invader.y + Constants.INVADER_HEIGHT
                    }

                    if (RectF.intersects(bulletRect, invaderRect)) {
                        score++
                        invader.isVisible = false
                        playerBullet.isActive = false

                        if(invaders.none { it.isVisible }) {
                            finishAndSave()
                        }
                    }

                    FirebaseDatabaseRepository.setPlayerBulletVisibility(gameId, playerRole, player1Bullet!!.isActive)
                    FirebaseDatabaseRepository.setPlayerBulletVisibility(gameId, opponentRole, player2Bullet!!.isActive)
                }
            }
        }

        FirebaseDatabaseRepository.updateInvaderArray(gameId, invaders)
        FirebaseDatabaseRepository.updateInvaderBulletsArray(gameId, invadersBullets)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun finishAndSave() {
        GlobalScope.launch {
            this@SpaceInvadersView.repo.insert(PlayerModel(score))
        }.invokeOnCompletion {
            FirebaseDatabaseRepository.endGame(gameId)
        }
    }
}