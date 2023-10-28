package com.example.spaceinvaderslight.model

import android.graphics.RectF


class Player(val height: Int, val width: Int, private val speed: Float,
             initialX: Float, initialY: Float, private val screenWidth: Int) {

    enum class MoveDirection { STOPPED, LEFT, RIGHT }

    var x = 0.0f
    var y = 0.0f
    var visible = false
    var lives = 3

    private var shipMoving = MoveDirection.STOPPED

    init {
        x = initialX
        y = initialY
    }

    fun setMovementDirection(direction: MoveDirection) {
        shipMoving = direction
    }

    fun update(fps: Long) {
        if (shipMoving == MoveDirection.LEFT) if (x * screenWidth > width * 0.1) x -= speed / fps
        if (shipMoving == MoveDirection.RIGHT) if (x * screenWidth < width * 9.0) x += speed / fps

        shipMoving = MoveDirection.STOPPED
    }
}