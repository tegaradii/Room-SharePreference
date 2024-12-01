package com.example.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Voter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val nik: String,
    val gender: String,
    val address: String
)



