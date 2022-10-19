package fi.prabin.praliamentappproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.adaptar.MemberListAdapter
import fi.prabin.praliamentappproject.databinding.FragmentMemberListBinding
import fi.prabin.praliamentappproject.viewmodel.FragmentMemberListViewModel
import fi.prabin.praliamentappproject.viewmodel.FragmentMemberListViewModelFactory


class MemberListFragment : Fragment() {

    private lateinit var adapter: MemberListAdapter

    private lateinit var viewModel : FragmentMemberListViewModel

    private var _binding: FragmentMemberListBinding? = null

    private val binding get() = _binding!!

    var party : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        party = arguments?.getString("party")

        // Inflate the layout for this fragment
        _binding = FragmentMemberListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.memberRecyclerView

        // set a LinearLayoutManager with default orientation
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProvider(
            this, FragmentMemberListViewModelFactory
                (requireActivity().application)
        )[FragmentMemberListViewModel::class.java]

        viewModel.getMembersByParty(party.toString())

        //Observe member list in view model and display the list of members
        viewModel.membersList.observe(viewLifecycleOwner) { members ->
            adapter = MemberListAdapter(members)
            binding.memberRecyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : MemberListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                    Log.d("CLicked members", members[position].firstname)

                    findNavController().navigate(
                        R.id.action_memberListFragment_to_memberDetailFragment
                    )
                }
            })
        }
    }

}