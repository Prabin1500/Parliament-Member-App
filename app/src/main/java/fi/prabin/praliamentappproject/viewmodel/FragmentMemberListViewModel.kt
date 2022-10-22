package fi.prabin.praliamentappproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.prabin.praliamentappproject.database.ParliamentMemberInfo
import fi.prabin.praliamentappproject.database.ParliamentMemberInfoRepository
import fi.prabin.praliamentappproject.database.ParliamentMemberRoomDatabase
import kotlinx.coroutines.launch

class FragmentMemberListViewModel(application: Application) : AndroidViewModel(application) {

    private val _membersList = MutableLiveData<List<ParliamentMemberInfo>>()
    val membersList: LiveData<List<ParliamentMemberInfo>> = _membersList

    private val _partyName = MutableLiveData<String>()
    val partyName : LiveData<String> = _partyName

    private val _number = MutableLiveData<String>()
    val number : LiveData<String> = _number

    //initialize the repository
    private val parliamentMemberInfoRepository = ParliamentMemberInfoRepository(
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberExtraInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).getNotesDao()
    )

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */
    fun getMembersByParty(partyName : String) {
        viewModelScope.launch {
            _membersList.value = parliamentMemberInfoRepository.findMembersByPartyName(partyName)

        }
    }
}

//Factory class for ListViewModel class
class FragmentMemberListViewModelFactory(val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentMemberListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FragmentMemberListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}