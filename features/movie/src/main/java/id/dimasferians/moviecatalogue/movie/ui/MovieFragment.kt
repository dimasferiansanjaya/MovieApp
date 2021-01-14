package id.dimasferians.moviecatalogue.movie.ui

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
import id.dimasferians.moviecatalogue.core.di.CoreComponentProvider
import id.dimasferians.moviecatalogue.core.domain.model.Movie
import id.dimasferians.moviecatalogue.core.ui.movie.MovieLoadStateAdapter
import id.dimasferians.moviecatalogue.core.ui.movie.MoviePagingAdapter
import id.dimasferians.moviecatalogue.core.utils.autoCleared
import id.dimasferians.moviecatalogue.movie.databinding.FragmentMovieBinding
import id.dimasferians.moviecatalogue.movie.di.DaggerMovieComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFragment : Fragment() {

    private var binding by autoCleared<FragmentMovieBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val movieViewModel: MovieViewModel by viewModels { viewModelFactory }

    private lateinit var movieAdapter: MoviePagingAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initObserver()
    }

    private fun initDependencyInjection() {
        val coreComponent = (requireActivity().application as CoreComponentProvider).provideCoreComponent()
        DaggerMovieComponent.factory()
            .create(coreComponent)
            .inject(this)
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.moviePagingData.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MoviePagingAdapter { movie ->
            navigateToMovieDetail(movie)
        }

        binding.layoutMovie.rvMovieTv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { movieAdapter.retry() },
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
            setHasFixedSize(true)
        }
    }

    private fun navigateToMovieDetail(movie: Movie) {
        val uri = Uri.parse("movieapp://moviecatalogue/detail/${movie.id}/movie")
        findNavController().navigate(uri)
    }

}