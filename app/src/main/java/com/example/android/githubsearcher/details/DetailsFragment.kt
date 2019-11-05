package com.example.android.githubsearcher.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.android.githubsearcher.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val searchResult = DetailsFragmentArgs.fromBundle(arguments!!).selectedResult
        val viewModelFactory = DetailsViewModelFactory(searchResult, application)
        val viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }
}
