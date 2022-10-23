package fi.prabin.praliamentappproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fi.prabin.praliamentappproject.database.Note
import fi.prabin.praliamentappproject.database.ParliamentMemberInfoRepository
import fi.prabin.praliamentappproject.database.ParliamentMemberRoomDatabase
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) : AndroidViewModel(application) {

    //Initializing the repository
    private val parliamentMemberInfoRepository = ParliamentMemberInfoRepository(
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).parliamentMemberExtraInfoDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).getNotesDao(),
        ParliamentMemberRoomDatabase.getDatabase(application).getLikeDao()
    )

    val allNotes : LiveData<List<Note>> = parliamentMemberInfoRepository.allNotes

    // calling a delete method from our repository to delete our note.
    fun deleteNote (note: Note) = viewModelScope.launch() {
        parliamentMemberInfoRepository.delete(note)
    }

    //calling a method from our repository to add a new note.
    fun addNote(note: Note) = viewModelScope.launch() {
        parliamentMemberInfoRepository.insert(note)
    }
}

//Factory class for NoteViewModel
class NoteViewModelFactory(val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}