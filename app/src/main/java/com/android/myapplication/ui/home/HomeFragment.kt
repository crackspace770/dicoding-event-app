package com.android.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.R
import com.android.myapplication.data.Result
import com.android.myapplication.data.adapter.FinishedAdapter
import com.android.myapplication.data.adapter.UpcomingAdapter
import com.android.myapplication.data.factory.ViewModelFactory
import com.android.myapplication.databinding.FragmentHomeBinding

class HomeFragment:Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val upcomingAdapter = UpcomingAdapter()
    private val finishedAdapter = FinishedAdapter()

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvInit()
        observeViewModel()

    }

    private fun rvInit() {

        binding.apply {

            rvUpcoming.adapter = upcomingAdapter
            rvFinished.adapter = finishedAdapter

            rvUpcoming.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingAdapter
            }

            rvFinished.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                adapter = finishedAdapter
            }

        }
    }

    private fun observeViewModel() {

        viewModel.getUpcomingEvents().observe(requireActivity()) { result->

            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val newsData = result.data
                        upcomingAdapter.submitList(newsData)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = getString(R.string.something_wrong)
                    }

                }
            }
        }

        viewModel.getAllEvents().observe(requireActivity()) {result->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val newsData = result.data
                        finishedAdapter.submitList(newsData)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = getString(R.string.something_wrong)
                    }

                }
            }

        }


    }

}