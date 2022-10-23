package fi.prabin.praliamentappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.database.Like
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

    var bundle = Bundle()
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

        binding.writeANote.setOnClickListener{
            hetekaId?.let { it1 ->
                bundle.putInt("hetekaID", it1) }

            findNavController().navigate(R.id.action_memberDetailFragment_to_noteFragment,bundle)
        }

        viewModel.addLikeInfo(Like(hetekaId,false, false))


        binding.thumbsUp.setOnClickListener {
            val p = viewModel.allLikeInfo.value
            if (p != null) {
                for (i in p.indices){
                    if(p[i].hetekaId == hetekaId){
                        if(p[i].like){
                            viewModel.updateInfo(false,false,hetekaId)
                            binding.thumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                            println("SecondClick : ")
                        }else{
                            viewModel.updateInfo(true,false,hetekaId)
                            binding.thumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                            binding.thumbsUp.setImageResource(R.drawable.thumbs_up_green)
                        }
                    }
                }
            }

        }

        binding.thumbsDown.setOnClickListener {
            val p = viewModel.allLikeInfo.value
            if (p != null) {
                for (i in p.indices){
                    if(p[i].hetekaId == hetekaId){
                        if(p[i].dislike){
                            binding.thumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                            viewModel.updateInfo(false,false,hetekaId)
                        }else{
                            viewModel.updateInfo(false,true,hetekaId)
                            binding.thumbsDown.setImageResource(R.drawable.thumbs_down_green)
                            binding.thumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                        }
                    }
                }
            }

        }

        viewModel.allLikeInfo.observe(viewLifecycleOwner){p ->
            for(i in p.indices){
                if(p[i].hetekaId == hetekaId){
                    if(p[i].like){
                        binding.thumbsUp.setImageResource(R.drawable.thumbs_up_green)
                        binding.likeCount.text = "1"
                        binding.dislikeCount.text = "0"
                    }else if(p[i].dislike){
                        binding.thumbsDown.setImageResource(R.drawable.thumbs_down_green)
                        binding.likeCount.text = "0"
                        binding.dislikeCount.text = "1"
                    }else {
                        binding.thumbsUp.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                        binding.thumbsDown.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                        binding.likeCount.text = "0"
                        binding.dislikeCount.text = "0"
                    }
                }
            }
        }

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