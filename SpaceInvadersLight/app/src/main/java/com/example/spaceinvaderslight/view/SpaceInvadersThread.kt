package com.example.spaceinvaderslight.view


class SpaceInvadersThread(private val spaceInvadersView: SpaceInvadersView
) : Thread() {
    var running = false

    private val targetFps: Long = 30
    private val targetTime = (1000 / this.targetFps)

    override fun run() {
        var startTime: Long
        var executionTime: Long
        var waitingTime: Long

        while (running) {
            startTime = System.nanoTime()

            synchronized(this) {
                spaceInvadersView.update(targetFps)
                spaceInvadersView.draw()
            }

            executionTime = (System.nanoTime() - startTime) / 1000000
            waitingTime = targetTime - executionTime
            if (waitingTime > 0) sleep(waitingTime)
        }
    }
}