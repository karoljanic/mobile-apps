package com.example.spaceinvaderslight.room


class PlayerRepository(private val playerDao: PlayerDao) {
    fun getAll(): List<PlayerModel> = playerDao.getAll()

    fun updateScore(first: Int) = playerDao.updateScore(first)

    fun insert(player: PlayerModel) = playerDao.insert(player)
}