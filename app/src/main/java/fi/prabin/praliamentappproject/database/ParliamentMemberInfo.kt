package fi.prabin.praliamentappproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ParliamentMembers_Information")
data class ParliamentMemberInfo(
    @PrimaryKey(autoGenerate = false)
    val hetekaId: Int,
    val seatNumber: Int,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String

)