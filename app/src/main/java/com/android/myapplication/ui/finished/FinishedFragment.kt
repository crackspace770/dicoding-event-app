package com.android.myapplication.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.R
import com.android.myapplication.data.adapter.FinishedAdapter
import com.android.myapplication.data.factory.ViewModelFactory
import com.android.myapplication.databinding.FragmentFinishedBinding
import com.android.myapplication.ui.home.HomeViewModel
import com.android.myapplication.data.Result

class FinishedFragment : Fragment() {

    private lateinit var binding: FragmentFinishedBinding

    private val finishedAdapter by lazy { FinishedAdapter() }

    private val viewModel: FinishedViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        rvSearch()
        observeSearchResult()

        // Show initial state message by default
        binding.onInitialSearchStateMessage.visibility = View.VISIBLE
        binding.rvCard.visibility = View.GONE
    }

    private fun rvSearch() {
        binding.rvCard.apply {
            adapter = finishedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search)
        search.expandActionView()

        val searchView = search.actionView as SearchView

        searchView.apply {
            onActionViewExpanded()
            setIconifiedByDefault(false)
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            requestFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    binding.rvCard.scrollToPosition(0)
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                            viewError.root.visibility = View.GONE
                            viewEmpty.root.visibility = View.GONE
                            onInitialSearchStateMessage.visibility = View.GONE
                            rvCard.visibility = View.VISIBLE
                            rvCard.scrollToPosition(0)
                        }
                        viewModel.setSearchQuery(query)
                        observeSearchResult()
                    } else {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            viewError.root.visibility = View.GONE
                            viewEmpty.root.visibility = View.GONE
                            onInitialSearchStateMessage.visibility = View.VISIBLE
                            rvCard.visibility = View.GONE
                        }
                    }
                    return true
                }
            })
        }
    }

    private fun observeSearchResult() {
        binding.apply {
            viewModel.searchResult.observe(viewLifecycleOwner) { results ->
                if (results != null) {
                    when (results) {
                        is Result.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            val searchResult = results.data
                            progressBar.visibility = View.GONE
                            finishedAdapter.submitList(searchResult)
                            rvCard.scrollToPosition(0)
                            if (searchResult.isEmpty()) {
                                viewEmpty.root.visibility = View.VISIBLE
                                onInitialSearchStateMessage.visibility = View.GONE
                            } else {
                                viewEmpty.root.visibility = View.GONE
                            }
                        }
                        is Result.Error -> {
                            progressBar.visibility = View.GONE
                            viewError.root.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}
