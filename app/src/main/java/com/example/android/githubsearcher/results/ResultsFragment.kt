package com.example.android.githubsearcher.results


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.githubsearcher.databinding.FragmentResultsBinding


class ResultsFragment : Fragment() {
    private val viewModel: ResultsViewModel by lazy {
        ViewModelProviders.of(this).get(ResultsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentResultsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.resultsRecyclerView.adapter = ResultsAdapter(ResultsAdapter.OnClickListener {
            viewModel.displayDetailsfragment(it)
        })

        viewModel.navigateToSelectedResult.observe(this, Observer {
            it?.let {
                this.findNavController()
                    .navigate(ResultsFragmentDirections.actionResultsFragmentToDetailsFragment(it))
                viewModel.navigateToDetailsFragmentComplete()
            }
        })

        binding.searchActivityGoButton.setOnClickListener {
            viewModel.processSearch(binding.resultsEditText.text.toString())
        }
        return binding.root
    }
}
