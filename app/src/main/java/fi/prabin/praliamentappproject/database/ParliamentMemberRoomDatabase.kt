package fi.prabin.praliamentappproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParliamentMemberInfo::class, ParliamentMemberExtraInfo::class],
    version = 1, exportSchema = false)

abstract class ParliamentMemberRoomDatabase : RoomDatabase() {

    abstract fun parliamentMemberInfoDao() : ParliamentMemberInfoDao
    abstract fun parliamentMemberExtraInfoDao() : ParliamentMemberExtraInfoDao
    //abstract fun getNotesDao(): NotesDao
    //abstract fun getLikeDao(): LikeCountDao

    companion object {
        @Volatile
        private var INSTANCE: ParliamentMemberRoomDatabase? = null

        fun getDatabase(context: Context): ParliamentMemberRoomDatabase {

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParliamentMemberRoomDatabase::class.java,
                    "parliamentMembers_database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}