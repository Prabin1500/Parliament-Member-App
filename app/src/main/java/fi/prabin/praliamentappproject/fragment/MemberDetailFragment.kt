package fi.prabin.praliamentappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.databinding.FragmentMemberDetailBinding
import fi.prabin.praliamentappproject.viewmodel.FragmentMemberDetailViewModel

/**
* Fragment which stores the detail information of the members.
*/
class MemberDetailFragment : Fragment() {

    private var _binding: FragmentMemberDetailBinding? = null
    private val binding get() = _binding!!

    var hetekaId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Get the hetekaId's value passed from Member List Fragment
        hetekaId = arguments?.getInt("hetekaID")

        // Inflate the layout for this fragment
        _binding = FragmentMemberDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}