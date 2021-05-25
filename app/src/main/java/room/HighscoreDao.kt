package room;

import androidx.room.*

@Dao
interface HighscoreDao {

    @Insert
    fun insertNewPlayer(highscoreItem: HighscoreItem)

    @Query ("SELECT * FROM highscoreitem")
    fun getAll() : MutableList<HighscoreItem>

    @Query ("SELECT * FROM highscoreitem WHERE player_name = :name")
    fun getPlayer(name : String) : MutableList<HighscoreItem>

    @Query ("UPDATE highscoreitem SET player_highscore = :score, date = :date WHERE player_name = :name")
    fun updateScore(name : String, score : Int, date : Long)

    @Delete
    fun deleteScore(item : HighscoreItem)

}