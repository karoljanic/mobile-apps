package com.example.spaceinvaderslight.model

import kotlin.random.Random


class Invader(
    private var speed: Float = 0.0f,
    initialX: Float = 0.0f,
    initialY: Float = 0.0f
) {

    enum class MoveDirection { STOPPED, LEFT, RIGHT }

    private val SPEED_UP_FACTOR = 1.05f

    var x = 0f
    var y = 0f

    private var shipMoving = MoveDirection.RIGHT
    var isVisible = false

    init {
        isVisible = true

        x = initialX
        y = initialY

    }

    fun update(fps: Long) {
        if (shipMoving == MoveDirection.LEFT) x -= speed / fps
        if (shipMoving == MoveDirection.RIGHT) x += speed / fps
    }

    fun dropDownAndReverse(dy: Float) {
        shipMoving = if (shipMoving == MoveDirection.LEFT) MoveDirection.RIGHT else MoveDirection.LEFT
        y += dy
        speed *= SPEED_UP_FACTOR
    }

    fun takeAim(playerShipX: Float, playerShipLength: Float): Boolean {
        if (x > playerShipX && x < playerShipX + playerShipLength) {
            val randomNumber = Random.nextInt(150)
            if (randomNumber == 0)
                return true
        }

        val randomNumber = Random.nextInt(1500)
        return randomNumber == 0
    }
}