package fi.prabin.praliamentappproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.prabin.praliamentappproject.database.ParliamentMemberInfoRepository
import fi.prabin.praliamentappproject.database.ParliamentMemberRoomDatabase
import kotlinx.coroutines.launch

class FragmentPartyListViewModel(application: Application): AndroidViewModel(application){

    private val _partyName = MutableLiveData<List<String>>()
    val partyName: LiveData<List<String>> = _partyName

    private val parliamentMemberInfoRepository = ParliamentMemberInfoRepository(
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberExtraInfoDao()
    )

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */
    fun getPartyList() {
        viewModelScope.launch {
            _partyName.value = parliamentMemberInfoRepository.getPartyName()
        }
    }
}

class FragmentPartyListViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentPartyListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FragmentPartyListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}