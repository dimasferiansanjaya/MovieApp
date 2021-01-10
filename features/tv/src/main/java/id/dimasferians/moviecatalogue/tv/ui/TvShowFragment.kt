package id.dimasferians.moviecatalogue.ui.tv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.dimasferians.moviecatalogue.MovieApp
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowLoadStateAdapter
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowPagingAdapter
import id.dimasferians.moviecatalogue.databinding.FragmentTvShowBinding
import id.dimasferians.moviecatalogue.di.DaggerAppComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val tvShowViewModel: TvShowViewModel by viewModels { viewModelFactory }
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
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initObserver()
    }

    private fun initDependencyInjection() {
        DaggerAppComponent.factory()
            .create(MovieApp.coreComponent)
            .inject(this)
    }


    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            tvShowViewModel.tvShowPagingData.collectLatest {
                tvAdapter.submitData(it)
            }
        }
    }

    private fun setupRecyclerView() {
        tvAdapter = TvShowPagingAdapter { tv ->
            navigateToMovieDetail(tv)
        }

        binding.layoutTv.rvMovieTv.apply {
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
            TvShowFragmentDirections.actionNavigationTvShowToDetailFragment(tv.id, "tv")
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}