package fi.prabin.praliamentappproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParliamentMemberExtraInfoDao {

    @Query("SELECT * FROM ParliamentMembers_ExtraInformation")
    fun getAllExtraData(): List<ParliamentMemberExtraInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExtraInfo(extra : ParliamentMemberExtraInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllExtraInfo(extra : List<ParliamentMemberExtraInfo>)
}