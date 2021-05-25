package room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
data class HighscoreItem (
    @PrimaryKey (autoGenerate = true)
    val uid : Int,
    @ColumnInfo(name = "player_name")
    val name:  String?,
    @ColumnInfo(name = "player_highscore")
    val  highscore: Int,
    @ColumnInfo(name = "date")
    val date : Long
)
