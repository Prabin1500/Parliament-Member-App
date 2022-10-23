package fi.prabin.praliamentappproject.database

import androidx.lifecycle.LiveData

class ParliamentMemberInfoRepository(
    private val parliamentMembersInfoDao : ParliamentMemberInfoDao,
    private val parliamentMemberExtraInfoDao: ParliamentMemberExtraInfoDao,
    private val notesDao: NotesDao,
    private val likeDao: LikeDao
) {

    val readAllMemberInfo = parliamentMembersInfoDao.getAllData()
    val readAllExtraInfo = parliamentMemberExtraInfoDao.getAllExtraData()
    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()
    val allLikes : LiveData<List<Like>> = likeDao.getAllLike()

    suspend fun addAllMembers(members : List<ParliamentMemberInfo>) =
        parliamentMembersInfoDao.insertAllMembers(members)

    fun findMembersByPartyName(party : String) : List<ParliamentMemberInfo> =
        parliamentMembersInfoDao.findMemberByPartyName(party)

    suspend fun getPartyName() : List<String> =
        parliamentMembersInfoDao.getPartyName()

    suspend fun addAllExtraInfo(extra : List<ParliamentMemberExtraInfo>) =
        parliamentMemberExtraInfoDao.addAllExtraInfo(extra)

    suspend fun addMember(member: List<ParliamentMemberInfo>) =
        parliamentMembersInfoDao.insertAllMembers(member)

    //For storing, deleting and updating Notes
    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    //For like and dislike count
    suspend fun addLikeCount(like: Like){
        likeDao.insert(like)
    }
    fun updateLike(like :Boolean, dislike : Boolean, id : Int?){
        likeDao.updateInfo(like, dislike, id)
    }

}