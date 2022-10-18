package fi.prabin.praliamentappproject.database

import androidx.lifecycle.LiveData

class ParliamentMemberInfoRepository(
    private val parliamentMembersInfoDao : ParliamentMemberInfoDao,
    private val parliamentMemberExtraInfoDao: ParliamentMemberExtraInfoDao
) {

    val readAllMemberInfo = parliamentMembersInfoDao.getAllData()
    val readAllExtraInfo = parliamentMemberExtraInfoDao.getAllExtraData()

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


}