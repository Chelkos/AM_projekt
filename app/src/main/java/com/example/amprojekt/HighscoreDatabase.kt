package com.example.amprojekt;

import androidx.room.Database;

@Database(entities = arrayOf(HighscoreItem::class), version = 1)
abstract class HighscoreDatabase {
    abstract fun highscoreDao(): HighscoreDao
}
