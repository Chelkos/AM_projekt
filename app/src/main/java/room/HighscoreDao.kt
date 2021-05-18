package room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query

@Dao
interface HighscoreDao {
    @Insert
    fun insertNewPlayer(vararg highscoreItem: HighscoreItem)
    @Query ("SELECT * FROM highscoreitem")
    fun getAll()
}