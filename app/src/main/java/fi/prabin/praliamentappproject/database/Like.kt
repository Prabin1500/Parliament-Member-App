package fi.prabin.praliamentappproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "like_table")
class Like(
    @PrimaryKey(autoGenerate = false)
    val hetekaId: Int?,
    val like : Boolean,
    val dislike : Boolean
)