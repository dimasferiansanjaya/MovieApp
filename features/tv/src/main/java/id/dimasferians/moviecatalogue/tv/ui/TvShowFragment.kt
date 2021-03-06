package id.dimasferians.moviecatalogue.tv.ui

import android.content.Context
import android.net.Uri
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
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowItemListener
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowLoadStateAdapter
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowPagingAdapter
import id.dimasferians.moviecatalogue.core.utils.viewBindings
import id.dimasferians.moviecatalogue.core.utils.provideCoreComponent
import id.dimasferians.moviecatalogue.tv.databinding.FragmentTvShowBinding
import id.dimasferians.moviecatalogue.tv.di.DaggerTvShowComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowFragment : Fragment(), TvShowItemListener {

    private var binding : FragmentTvShowBinding by viewBindings()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val tvShowViewModel: TvShowViewModel by viewModels { viewModelFactory }
    private var tvAdapter: TvShowPagingAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initObserver()
    }

    private fun initDependencyInjection() {
        DaggerTvShowComponent.factory()
            .create(provideCoreComponent())
            .inject(this)
    }


    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            tvShowViewModel.tvShowPagingData.collectLatest {
                tvAdapter?.submitData(it)
            }
        }
    }

    private fun setupRecyclerView() {
        tvAdapter = TvShowPagingAdapter(this)
        binding.layoutTv.rvMovieTv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tvAdapter?.withLoadStateHeaderAndFooter(
                header = TvShowLoadStateAdapter { tvAdapter?.retry() },
                footer = TvShowLoadStateAdapter { tvAdapter?.retry() }
            )
            setHasFixedSize(true)
        }
    }

    private fun navigateToMovieDetail(tv: TvShow) {
        val uri = Uri.parse("movieapp://moviecatalogue/detail/${tv.id}/tv")
        findNavController().navigate(uri)
    }

    override fun onItemClicked(tvShow: TvShow) {
        navigateToMovieDetail(tvShow)
    }

    // avoid memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        tvAdapter = null
    }

}