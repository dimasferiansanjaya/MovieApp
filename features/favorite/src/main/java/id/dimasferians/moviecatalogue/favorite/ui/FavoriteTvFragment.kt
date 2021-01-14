package id.dimasferians.moviecatalogue.favorite.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.databinding.LayoutMovieTvBinding
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.core.utils.autoCleared
import id.dimasferians.moviecatalogue.core.utils.hide
import id.dimasferians.moviecatalogue.core.utils.provideCoreComponent
import id.dimasferians.moviecatalogue.core.utils.show
import id.dimasferians.moviecatalogue.favorite.di.DaggerFavoriteComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTvFragment : Fragment() {

    private var binding by autoCleared<LayoutMovieTvBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FavoriteViewModel by viewModels { viewModelFactory }
    private lateinit var tvShowAdapter: FavoriteTvShowAdapter

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

    private fun initDependencyInjection() {
        DaggerFavoriteComponent.factory()
            .create(provideCoreComponent())
            .inject(this)
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteTvShow.collectLatest {
                if (it.isNotEmpty()) {
                    tvShowAdapter.setData(it)
                    binding.layoutEmpty.hide()
                    binding.rvMovieTv.show()
                } else {
                    binding.layoutEmpty.show()
                    binding.rvMovieTv.hide()
                    binding.imgEmpty.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_playlist
                        )
                    )
                    binding.messageTitle.text = getString(R.string.no_favorite_tv_show)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        tvShowAdapter = FavoriteTvShowAdapter { tv ->
            navigateToMovieDetail(tv)
        }

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
}