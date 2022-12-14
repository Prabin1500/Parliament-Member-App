package fi.prabin.praliamentappproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.prabin.praliamentappproject.database.*
import kotlinx.coroutines.launch

class FragmentMemberDetailViewModel(application: Application) : AndroidViewModel(application) {

    //All the listed members
    private val _member= MutableLiveData<List<ParliamentMemberInfo>>()
    val member: LiveData<List<ParliamentMemberInfo>> = _member

    private val _extraInfo = MutableLiveData<List<ParliamentMemberExtraInfo>>()
    val extraInfo : LiveData<List<ParliamentMemberExtraInfo>> = _extraInfo

    //Initialize the repository
    private val parliamentMemberInfoRepository = ParliamentMemberInfoRepository(
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberExtraInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).getNotesDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).getLikeDao()
    )
    val allLikeInfo = parliamentMemberInfoRepository.allLikes


    //Get all members data from the Room Database
    fun getAllMemberInfo(){
        viewModelScope.launch {
            _member.value = parliamentMemberInfoRepository.readAllMemberInfo
        }
    }

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */
    fun getAllExtraInfo(){
        viewModelScope.launch {
            _extraInfo.value = parliamentMemberInfoRepository.readAllExtraInfo
        }
    }

    fun addLikeInfo(like : Like){
        viewModelScope.launch {
            parliamentMemberInfoRepository.addLikeCount(like)
        }
    }

    fun updateInfo (like :Boolean, dislike : Boolean, id : Int?){
        viewModelScope.launch {
            parliamentMemberInfoRepository.updateLike(like, dislike, id)
        }
    }
}

//Factory class for DetailViewModel class
class MemberDetailViewModelFactory(val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentMemberDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FragmentMemberDetailViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}