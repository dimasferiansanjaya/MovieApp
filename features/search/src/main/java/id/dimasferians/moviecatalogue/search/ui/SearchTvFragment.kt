package id.dimasferians.moviecatalogue.search.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import id.dimasferians.moviecatalogue.core.R
import id.dimasferians.moviecatalogue.core.databinding.LayoutMovieTvBinding
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowLoadStateAdapter
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowPagingAdapter
import id.dimasferians.moviecatalogue.core.utils.autoCleared
import id.dimasferians.moviecatalogue.core.utils.hide
import id.dimasferians.moviecatalogue.core.utils.provideCoreComponent
import id.dimasferians.moviecatalogue.core.utils.show
import id.dimasferians.moviecatalogue.search.di.DaggerSearchComponent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchTvFragment : Fragment() {

    private var binding by autoCleared<LayoutMovieTvBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SearchViewModel by activityViewModels {viewModelFactory}
    private lateinit var tvAdapter: TvShowPagingAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutMovieTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initObserver()
    }

    private fun initDependencyInjection() {
        DaggerSearchComponent.factory()
            .create(provideCoreComponent())
            .inject(this)
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
        val uri = Uri.parse("movieapp://moviecatalogue/detail/${tv.id}/tv")
        findNavController().navigate(uri)
    }

}