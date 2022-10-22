package fi.prabin.praliamentappproject.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.adaptar.NoteClickDeleteInterface
import fi.prabin.praliamentappproject.adaptar.NotesAdapter
import fi.prabin.praliamentappproject.database.Note
import fi.prabin.praliamentappproject.databinding.FragmentNoteBinding
import fi.prabin.praliamentappproject.viewmodel.NoteViewModel
import fi.prabin.praliamentappproject.viewmodel.NoteViewModelFactory
import kotlinx.android.synthetic.main.fragment_member_detail.*
import kotlinx.android.synthetic.main.fragment_note.*
import java.text.SimpleDateFormat
import java.util.*


class NoteFragment : Fragment(), NoteClickDeleteInterface {

    private lateinit var adapter: NotesAdapter

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : NoteViewModel

    var hetekaId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hetekaId = arguments?.getInt("hetekaID")

        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater,container,false)

        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.notesRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        //Initializing the viewModel
        viewModel = ViewModelProvider(this, NoteViewModelFactory
            (requireActivity().application))[NoteViewModel::class.java]


        /**
         * Calling all notes method from the view model class to Observe the changes in the list
         * Checks if hetekaId of member matches with the corresponding Note.
         * And shows only the notes related to the specific member.
         */
        viewModel.allNotes.observe(viewLifecycleOwner){ list ->
            val allNotes = ArrayList<Note>()
            allNotes.clear()
            for ( i in list.indices){
                if(list[i].hetekaId1 == hetekaId){
                    allNotes.add(list[i])
                }
            }
            val notesAdapter = NotesAdapter(requireContext(),this,allNotes)
            recyclerView.adapter = notesAdapter
        }

        binding.btnSave.setOnClickListener{
            saveNote()
        }
    }

    //saves a note to the room database
    private fun saveNote() {
        if(etNote.text.isNotEmpty()){
            val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
            val currentDateAndTime: String = sdf.format(Date())
            val noteDescription = etNote.text.toString()
            viewModel.addNote(Note(hetekaId, noteDescription,currentDateAndTime))
        }

        etNote.setText("")

    }

    //delete a note from room database
    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}