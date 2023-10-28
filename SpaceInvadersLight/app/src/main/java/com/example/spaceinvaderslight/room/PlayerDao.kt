package com.example.spaceinvaderslight.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player ORDER BY score DESC")
    fun getAll(): List<PlayerModel>

    @Query("UPDATE player SET score = :first")
    fun updateScore(first: Int)

    @Insert
    fun insert(player: PlayerModel)
}