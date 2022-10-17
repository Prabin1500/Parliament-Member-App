package fi.prabin.praliamentappproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ParliamentMembers_ExtraInformation")
data class ParliamentMemberExtraInfo(
    @PrimaryKey(autoGenerate = false)
    val hetekaId: Int,
    val twitter: String,
    val bornYear: Int,
    val constituency: String
)