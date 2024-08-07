package com.android.myapplication.ui.active

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.R
import com.android.myapplication.data.adapter.ActiveAdapter
import com.android.myapplication.data.adapter.FinishedAdapter
import com.android.myapplication.data.factory.ViewModelFactory
import com.android.myapplication.databinding.FragmentActiveBinding
import com.android.myapplication.databinding.FragmentFinishedBinding
import com.android.myapplication.ui.finished.FinishedViewModel

class ActiveFragment:Fragment() {

    private lateinit var binding: FragmentActiveBinding
    private val activeAdapter by lazy { ActiveAdapter() }
    private val viewModel: ActiveViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBookmarkedEvent().observe(viewLifecycleOwner) { bookmarkedEvent ->
            activeAdapter.submitList(bookmarkedEvent)
            binding.progressBar.visibility = View.GONE
            binding.viewError.tvError.text = getString(R.string.no_data)
            binding.viewError.root.visibility =
                if (bookmarkedEvent.isNotEmpty()) View.GONE else View.VISIBLE
        }

        rvBookmark()

    }

    private fun rvBookmark() {
        binding.rvFavorite.apply {
            adapter = activeAdapter // Change this line
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

}