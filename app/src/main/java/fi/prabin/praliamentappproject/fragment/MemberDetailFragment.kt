package fi.prabin.praliamentappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.databinding.FragmentMemberDetailBinding
import fi.prabin.praliamentappproject.viewmodel.FragmentMemberDetailViewModel
import fi.prabin.praliamentappproject.viewmodel.MemberDetailViewModelFactory

/**
* Fragment which stores the detail information of the members.
*/
class MemberDetailFragment : Fragment() {

    private var _binding: FragmentMemberDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : FragmentMemberDetailViewModel

    var hetekaId : Int? = null
    val baseUrl = "https://avoindata.eduskunta.fi/"

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

        viewModel = ViewModelProvider(this, MemberDetailViewModelFactory
            ((requireActivity().application)))[FragmentMemberDetailViewModel::class.java]

        viewModel.getAllMemberInfo()
        viewModel.getAllExtraInfo()

        //Observe the member detail in ViewModel and display the information
        viewModel.member.observe(viewLifecycleOwner){p ->
            for (i in p.indices){
                /*
                Checks if hetekaId of the clicked Member which is passed from MemberListFragment
                matches the hetekaId from the list
                */
                if(p[i].hetekaId == hetekaId ){
                    val url = p[i].pictureUrl
                    val firstName = p[i].firstname
                    val lastName = p[i].lastname
                    val name = "$firstName $lastName"
                    binding.nameOfMPDetail.text = name
                    binding.nameOfPartyDetail.text = p[i].party
                    binding.hetekaIdValueDetail.text = p[i].hetekaId.toString()
                    binding.seatNoValueDetail.text = p[i].seatNumber.toString()
                    binding.isMinisterValueDetail.text = if(p[i].minister) "Yes" else "No"
                    Glide.with(this).load(baseUrl + url).into(binding.imageOfMpDetail)
                }
            }
        }

        //Observe the extra Info of members in View model and display the information
        viewModel.extraInfo.observe(viewLifecycleOwner){ p->
            for(i in p.indices){
                if(p[i].hetekaId == hetekaId ){
                    binding.dobOfMP.text = p[i].bornYear.toString()
                    binding.constituencyValue.text = p[i].constituency
                    binding.twitterAccountValue.text = if(p[i].twitter == "") "Not available" else p[i].twitter
                }
            }
        }

    }
    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}