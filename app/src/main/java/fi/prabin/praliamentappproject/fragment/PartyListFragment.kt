package fi.prabin.praliamentappproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.adaptar.PartyListAdapter
import fi.prabin.praliamentappproject.databinding.FragmentPartyListBinding
import fi.prabin.praliamentappproject.viewmodel.FragmentPartyListViewModel
import fi.prabin.praliamentappproject.viewmodel.FragmentPartyListViewModelFactory


class PartyListFragment : Fragment() {

    private lateinit var adapter : PartyListAdapter

    private lateinit var viewModel : FragmentPartyListViewModel

    private var _binding : FragmentPartyListBinding? = null

    private val binding get() = _binding!!

    var party = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPartyListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this, FragmentPartyListViewModelFactory
            (requireActivity().application))[FragmentPartyListViewModel::class.java]

        val recyclerView = binding.partyRecyclerView

        //Get list of parties from ViewModel
        viewModel.getPartyList()

        //Observe the partyName LiveData
        viewModel.partyName.observe(viewLifecycleOwner) { partyName ->
            party.clear()
            val p = partyName.distinct().sorted()
            for (i in p.indices){
                party.add(partyName.distinct().sorted()[i])
            }

            adapter = PartyListAdapter(party)
            recyclerView.adapter = adapter

            //Setup a click listener of parties
            adapter.setOnItemClickListener(object : PartyListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    Log.d("CLicked parties", party[position])
                    findNavController().navigate(R.id.action_partyListFragment_to_memberListFragment)
                }
            })
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}