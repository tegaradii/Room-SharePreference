package com.example.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import androidx.room.*

@Dao
interface VoterDao {

    @Query("SELECT * FROM Voter")
    suspend fun getAllVoters(): List<Voter>

    @Query("SELECT * FROM Voter WHERE id = :voterId")
    suspend fun getVoterById(voterId: Int): Voter?

    @Insert
    suspend fun insertVoter(voter: Voter)

    @Update
    suspend fun updateVoter(voter: Voter)

    @Delete
    suspend fun deleteVoter(voter: Voter)
}
