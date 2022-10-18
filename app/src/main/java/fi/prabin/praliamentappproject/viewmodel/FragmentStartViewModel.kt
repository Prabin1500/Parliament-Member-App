package fi.prabin.praliamentappproject.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import fi.prabin.praliamentappproject.database.ParliamentMemberExtraInfo
import fi.prabin.praliamentappproject.database.ParliamentMemberInfo
import fi.prabin.praliamentappproject.database.ParliamentMemberInfoRepository
import fi.prabin.praliamentappproject.database.ParliamentMemberRoomDatabase
import fi.prabin.praliamentappproject.network.ExtraInformationApi
import fi.prabin.praliamentappproject.network.MemberInformationApi
import kotlinx.coroutines.launch

class FragmentStartViewModel(application: Application) : AndroidViewModel(application) {

    private val _membersList = MutableLiveData<List<ParliamentMemberInfo>>()
    val membersList: LiveData<List<ParliamentMemberInfo>> = _membersList

    private val _membersListExtraInfo = MutableLiveData<List<ParliamentMemberExtraInfo>>()
    val membersListExtraInfo: LiveData<List<ParliamentMemberExtraInfo>> = _membersListExtraInfo

    //Initialize the repository
    private val parliamentMemberInfoRepository = ParliamentMemberInfoRepository(
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberExtraInfoDao()
    )

    //get member information from the network
    fun getMemberInformation() {
        viewModelScope.launch {
            try {
                _membersList.value = MemberInformationApi.retrofitService.getList()
                Log.d("Network", "Fetching data")
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "Error :$e")
            }
        }
    }

    //Save member informatino to the room database
    fun saveToRoomDatabase() {
        val newMembersList: List<ParliamentMemberInfo>? = _membersList.value

        if (newMembersList != null) {
            Log.d("Info","SAVING TO DATABASE")
            viewModelScope.launch {
                parliamentMemberInfoRepository.addAllMembers(newMembersList)
            }
        }
    }

    fun getExtraMemberInformation() {
        viewModelScope.launch {
            try {
                _membersListExtraInfo.value = ExtraInformationApi.retrofitService.getList()
                Log.d("Network", "Fetching data")
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "Error :$e")
            }
        }
    }

    fun saveExtraInfoToRoomDatabase() {
        val newExtrasList: List<ParliamentMemberExtraInfo>? = _membersListExtraInfo.value

        if (newExtrasList != null) {
            Log.d("Info","SAVING TO DATABASE")
            viewModelScope.launch {
                parliamentMemberInfoRepository.addAllExtraInfo(newExtrasList)
            }
        }
    }
}

//Factory class for Start view modal class
class FragmentStartViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FragmentStartViewModel::class.java)) {
            FragmentStartViewModel(this.app) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}