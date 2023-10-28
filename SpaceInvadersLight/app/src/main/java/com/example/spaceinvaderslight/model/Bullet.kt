package com.example.spaceinvaderslight.model

class Bullet(private val speed: Float = 0.0f) {

    enum class MoveDirection { STOPPED, UP, DOWN }

    var x = 0.0f
    var y = 0.0f
    var isActive = false

    private var direction = MoveDirection.STOPPED

    fun shoot(startX: Float, startY: Float, dir: MoveDirection) {
        if (!isActive) {
            x = startX
            y = startY
            direction = dir
            isActive = true
        }
    }

    fun update(fps: Long) {
        if(!isActive)
            return

        y = if (direction == MoveDirection.UP) y - speed / fps else y + speed / fps
    }
}