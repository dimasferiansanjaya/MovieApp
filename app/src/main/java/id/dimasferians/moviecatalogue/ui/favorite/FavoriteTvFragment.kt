package id.dimasferians.moviecatalogue.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.databinding.LayoutMovieTvBinding
import id.dimasferians.moviecatalogue.utils.hide
import id.dimasferians.moviecatalogue.utils.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteTvFragment : Fragment() {

    private var _binding: LayoutMovieTvBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var tvShowAdapter: FavoriteTvShowAdapter

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
        initData()
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
            FavoriteFragmentDirections.actionNavigationFavoriteToDetailFragment(tv.id, "tv")
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}