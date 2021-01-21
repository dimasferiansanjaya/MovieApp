package id.dimasferians.moviecatalogue.favorite.ui

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
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.databinding.LayoutMovieTvBinding
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.core.ui.tv.TvShowItemListener
import id.dimasferians.moviecatalogue.core.utils.hide
import id.dimasferians.moviecatalogue.core.utils.provideCoreComponent
import id.dimasferians.moviecatalogue.core.utils.show
import id.dimasferians.moviecatalogue.core.utils.viewBindings
import id.dimasferians.moviecatalogue.favorite.di.DaggerFavoriteComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvFragment : Fragment(), TvShowItemListener {

    private var binding: LayoutMovieTvBinding by viewBindings()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FavoriteViewModel by viewModels { viewModelFactory }
    private var tvShowAdapter: FavoriteTvShowAdapter? = null

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
        initData()
    }

    override fun onResume() {
        super.onResume()
        binding.imgEmpty.playAnimation()
    }

    private fun initDependencyInjection() {
        DaggerFavoriteComponent.factory()
            .create(provideCoreComponent())
            .inject(this)
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteTvShow.collectLatest {
                if (it.isNotEmpty()) {
                    tvShowAdapter?.setData(it)
                    showEmptyLayout(false)
                } else {
                    showEmptyLayout(true)
                }
            }
        }
    }

    private fun showEmptyLayout(state: Boolean) {
        with(binding) {
            if (state) {
                layoutEmpty.show()
                rvMovieTv.hide()
                imgEmpty.setAnimation(R.raw.empty_box)
                messageTitle.text = getString(R.string.no_favorite_tv_show)
            } else {
                layoutEmpty.hide()
                rvMovieTv.show()
            }
        }
    }

    private fun setupRecyclerView() {
        tvShowAdapter = FavoriteTvShowAdapter(this)
        binding.rvMovieTv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tvShowAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigateToMovieDetail(tv: TvShow) {
        val action =
            FavoriteFragmentDirections.actionNavigationFavoriteToNavigationDetail(tv.id, "tv")
        findNavController().navigate(action)
    }

    override fun onItemClicked(tvShow: TvShow) {
        navigateToMovieDetail(tvShow)
    }

    override fun onStop() {
        super.onStop()
        tvShowAdapter = null
    }
}