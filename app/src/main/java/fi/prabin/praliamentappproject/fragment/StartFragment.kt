package fi.prabin.praliamentappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fi.prabin.praliamentappproject.R
import fi.prabin.praliamentappproject.databinding.FragmentStartBinding
import fi.prabin.praliamentappproject.viewmodel.FragmentStartViewModel
import fi.prabin.praliamentappproject.viewmodel.FragmentStartViewModelFactory


class StartFragment : Fragment() {

    private lateinit var viewModel: FragmentStartViewModel
    private var _binding : FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentStartBinding.inflate(inflater,container,false)
        val view = binding.root

        viewModel = ViewModelProvider(this, FragmentStartViewModelFactory
            (requireActivity().application))[FragmentStartViewModel::class.java]

        viewModel.getMemberInformation()

        viewModel.getExtraMemberInformation()

        viewModel.saveExtraInfoToRoomDatabase()

        viewModel.saveToRoomDatabase()
        return view

    }


}