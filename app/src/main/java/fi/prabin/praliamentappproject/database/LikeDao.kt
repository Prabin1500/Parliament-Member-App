package fi.prabin.praliamentappproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Like)

    @Query("Select * from like_table")
    fun getAllLike(): LiveData<List<Like>>

    @Query("UPDATE like_table SET `like` = :like1 , dislike = :dislike1 where hetekaId lIKE :id ")
    fun updateInfo(like1 : Boolean, dislike1 : Boolean, id : Int?)

    @Query("UPDATE like_table SET `dislike` = :dislike1 ")
    fun updateDislike(dislike1 : Boolean)

}