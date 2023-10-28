package com.example.spaceinvaderslight.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class PlayerModel(
    @ColumnInfo(name = "score") val score: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}