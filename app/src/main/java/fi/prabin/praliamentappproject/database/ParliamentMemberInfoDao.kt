package fi.prabin.praliamentappproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ParliamentMemberInfoDao {

    @Query("SELECT * FROM ParliamentMembers_Information ORDER BY hetekaId ASC")
    fun getAllData(): List<ParliamentMemberInfo>

    @Query("SELECT * FROM ParliamentMembers_Information WHERE party LIKE :party")
    fun findMemberByPartyName(party: String): List<ParliamentMemberInfo>

    @Query("SELECT party FROM ParliamentMembers_Information" )
    suspend fun getPartyName(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMembers(members : List<ParliamentMemberInfo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMember(member: ParliamentMemberInfo)

}