package com.example.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room.Voter
import com.example.room.VoterDao


@Database(entities = [Voter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun voterDao(): VoterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "voter_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
