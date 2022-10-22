package fi.prabin.praliamentappproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note (
    val hetekaId1 : Int?,
    val note : String,
    val timeStamp : String
){
    @PrimaryKey(autoGenerate = true) var id = 0
}