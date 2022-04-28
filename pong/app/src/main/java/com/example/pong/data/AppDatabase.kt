package com.example.pong.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.Provider

@Database(entities = [(Score::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ScoreDAO() : ScoreDAO



}