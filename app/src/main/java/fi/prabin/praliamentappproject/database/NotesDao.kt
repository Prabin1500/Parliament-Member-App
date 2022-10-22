package fi.prabin.praliamentappproject.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {
    /**
     * below is the insert method for adding a new entry to our database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note :Note)

    @Delete
    suspend fun delete (note:Note)

    @Query("Select * from notesTable")
    fun getAllNotes(): LiveData<List<Note>>

}
