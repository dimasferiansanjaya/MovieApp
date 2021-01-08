package id.dimasferians.moviecatalogue.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.databinding.LayoutMovieTvBinding
import id.dimasferians.moviecatalogue.ui.tv.TvShowLoadStateAdapter
import id.dimasferians.moviecatalogue.ui.tv.TvShowPagingAdapter
import id.dimasferians.moviecatalogue.utils.hide
import id.dimasferians.moviecatalogue.utils.show
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchTvFragment : Fragment() {

    private var _binding: LayoutMovieTvBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var tvAdapter: TvShowPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutMovieTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.liveDataTv.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                tvAdapter.submitData(it)
            }
        }

        // show greeting layout
        viewModel.isEmptyQuery.observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutEmpty.show()
                binding.rvMovieTv.hide()
                binding.imgEmpty.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_playlist
                    )
                )
                binding.messageTitle.text = getString(R.string.hello)
                binding.messageValue.text = getString(R.string.find_tv_show)
            } else {
                binding.layoutEmpty.hide()
                binding.rvMovieTv.show()
            }
        }

        // show empty layout when paging data is null or empty
        viewLifecycleOwner.lifecycleScope.launch {
            tvAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    // capture data to list
                    val list = tvAdapter.snapshot()
                    if (list.isNullOrEmpty()) {
                        binding.layoutEmpty.show()
                        binding.rvMovieTv.hide()
                        binding.imgEmpty.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_not_found
                            )
                        )
                        binding.messageTitle.text = getString(R.string.oops__)
                        binding.messageValue.text = getString(R.string.empty_tv_message)
                    } else {
                        binding.layoutEmpty.hide()
                        binding.rvMovieTv.show()
                    }
                }
        }
    }

    private fun setupRecyclerView() {
        tvAdapter = TvShowPagingAdapter { tv ->
            navigateToMovieDetail(tv)
        }

        binding.rvMovieTv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tvAdapter.withLoadStateHeaderAndFooter(
                header = TvShowLoadStateAdapter { tvAdapter.retry() },
                footer = TvShowLoadStateAdapter { tvAdapter.retry() }
            )
            setHasFixedSize(true)
        }
    }

    private fun navigateToMovieDetail(tv: TvShow) {
        val action =
            SearchFragmentDirections.actionNavigationSearchToDetailFragment(tv.id, "tv")
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}