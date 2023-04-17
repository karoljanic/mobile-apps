package com.example.calendar.event

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDate

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("SELECT * FROM EventsTable;")
    fun getAll(): LiveData<List<Event>>

    @Query("DELETE FROM EventsTable;")
    suspend fun deleteAll()
    
}