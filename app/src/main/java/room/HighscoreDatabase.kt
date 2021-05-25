package room;

import androidx.room.Database;
import androidx.room.RoomDatabase

@Database(entities = [HighscoreItem::class], version = 1)
abstract class HighscoreDatabase  : RoomDatabase() {
    abstract fun highscoreDao(): HighscoreDao
}
