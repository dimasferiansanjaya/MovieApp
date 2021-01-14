package id.dimasferians.moviecatalogue.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import id.dimasferians.moviecatalogue.core.BuildConfig
import id.dimasferians.moviecatalogue.core.data.Resource
import id.dimasferians.moviecatalogue.core.domain.model.*
import id.dimasferians.moviecatalogue.core.utils.autoCleared
import id.dimasferians.moviecatalogue.core.utils.hide
import id.dimasferians.moviecatalogue.core.utils.provideCoreComponent
import id.dimasferians.moviecatalogue.core.utils.show
import id.dimasferians.moviecatalogue.detail.R
import id.dimasferians.moviecatalogue.detail.databinding.FragmentDetailBinding
import id.dimasferians.moviecatalogue.detail.di.DaggerDetailComponent
import javax.inject.Inject
import kotlin.math.abs

class DetailFragment : Fragment() {

    private var binding by autoCleared<FragmentDetailBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }
//    private val args: DetailFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDependencyInjection()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= 21) {
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.overlay_dark_30)
        }

        setAppBarScrollListener()

        val argsId = arguments?.getInt("id")
        val mediaType = arguments?.getString("mediatype")
        populateView(argsId, mediaType)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun initDependencyInjection() {
        DaggerDetailComponent.factory()
            .create(provideCoreComponent())
            .inject(this)
    }

    private fun populateView(id: Int?, media: String?) {
        if (id != null && media != null) {
            if (media == "movie") getDataMovie(id) else getDataTv(id)
        }
    }

    private fun getDataMovie(id: Int) {
        viewModel.getDetailMovie(id)
        viewModel.movieDetail.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.loading.show()
                }
                is Resource.Success -> {
                    binding.loading.hide()
                    state.data?.let {
                        renderMovieView(it)
                        setShareButton(it.title)
                    }
                }
                is Resource.Error -> {
                    binding.loading.hide()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getDataTv(id: Int) {
        viewModel.getDetailTv(id)
        viewModel.tvDetail.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.loading.show()
                }
                is Resource.Success -> {
                    binding.loading.hide()
                    state.data?.let {
                        renderTvView(it)
                        setShareButton(it.name)
                    }
                }
                is Resource.Error -> {
                    binding.loading.hide()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderMovieView(movie: MovieDetail) {
        Glide.with(requireContext())
            .load(
                if (movie.backdropImage == null) R.drawable.cover_placeholder else
                    BuildConfig.BASE_IMAGE_URL + movie.backdropImage
            )
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.cover_placeholder)
                    .transform(CenterCrop())
            )
            .into(binding.imgBackdrop)

        binding.toolbar.title = movie.title
        binding.rating.setProgress(movie.voteAverage.times(10).toFloat(), true)
        binding.rating.progressColor = when (movie.voteAverage.times(10).toInt()) {
            in 0..39 -> ContextCompat.getColor(binding.rating.context, R.color.red)
            in 40..69 -> ContextCompat.getColor(binding.rating.context, R.color.gold)
            else -> ContextCompat.getColor(binding.rating.context, R.color.green)
        }
        binding.rating.textColor = binding.rating.progressColor
        binding.status.text = movie.status
        binding.overview.text =
            if (movie.overview == "") getString(R.string.no_overview) else movie.overview
        setGenresChip(movie.genre)

        val mv = Movie(
            movie.id,
            movie.title,
            movie.overview,
            movie.image,
            movie.voteAverage,
            movie.voteCount
        )

        viewModel.isFavoriteMovie(movie.id).observe(viewLifecycleOwner) {
            if (it != null) {
                setFavoriteIcon(true)
                binding.fabFavorite.setOnClickListener { view ->
                    viewModel.deleteFavoriteMovie(mv)
                    Snackbar.make(
                        view,
                        "${movie.title} successfully removed from favorite.",
                        Snackbar.LENGTH_SHORT
                    ).setAction("UNDO") {
                        viewModel.setFavoriteMovie(mv)
                    }.show()
                }
            } else {
                setFavoriteIcon(false)
                binding.fabFavorite.setOnClickListener { view ->
                    viewModel.setFavoriteMovie(mv)
                    Snackbar.make(
                        view,
                        "${movie.title} successfully added to favorite.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun renderTvView(tv: TvShowDetail) {
        Glide.with(requireContext())
            .load(
                if (tv.backdropImage == null) R.drawable.cover_placeholder else
                    BuildConfig.BASE_IMAGE_URL + tv.backdropImage
            )
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.cover_placeholder)
                    .transform(CenterCrop())
            )
            .into(binding.imgBackdrop)

        binding.toolbar.title = tv.name
        binding.rating.setProgress(tv.voteAverage.times(10).toFloat(), true)
        binding.rating.progressColor = when (tv.voteAverage.times(10).toInt()) {
            in 0..39 -> ContextCompat.getColor(binding.rating.context, R.color.red)
            in 40..69 -> ContextCompat.getColor(binding.rating.context, R.color.gold)
            else -> ContextCompat.getColor(binding.rating.context, R.color.green)
        }
        binding.rating.textColor = binding.rating.progressColor
        binding.status.text = tv.status
        binding.overview.text =
            if (tv.overview == "") getString(R.string.no_overview) else tv.overview
        setGenresChip(tv.genre)

        val tvShow = TvShow(tv.id, tv.name, tv.overview, tv.image, tv.voteAverage, tv.voteCount)

        viewModel.isFavoriteTvShow(tv.id).observe(viewLifecycleOwner) {
            if (it != null) {
                setFavoriteIcon(true)
                binding.fabFavorite.setOnClickListener { view ->
                    viewModel.deleteFavoriteTvShow(tvShow)
                    Snackbar.make(
                        view,
                        "${tv.name} successfully removed from favorite.",
                        Snackbar.LENGTH_SHORT
                    ).setAction("UNDO") {
                        viewModel.setFavoriteTvShow(tvShow)
                    }.show()
                }
            } else {
                setFavoriteIcon(false)
                binding.fabFavorite.setOnClickListener { view ->
                    viewModel.setFavoriteTvShow(tvShow)
                    Snackbar.make(
                        view,
                        "${tv.name} successfully added to favorite.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_favorite)
            binding.fabFavorite.setImageDrawable(drawable)
        } else {
            val drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_round_favorite_border)
            binding.fabFavorite.setImageDrawable(drawable)
        }
    }

    private fun setGenresChip(genres: List<Genre>) {
        genres.forEach {
            val inflater = LayoutInflater.from(binding.genreChipGroup.context)
            val layoutRes = R.layout.item_genre
            val parent = binding.genreChipGroup
            val chip = (inflater.inflate(layoutRes, parent, false) as Chip).apply {
                text = it.name
                isCheckable = false
                isClickable = false
            }
            binding.genreChipGroup.addView(chip)
        }
    }

    private fun setAppBarScrollListener() {
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            if (abs(i) - appBarLayout.totalScrollRange == 0) {
                if (Build.VERSION.SDK_INT >= 21) {
                    requireActivity().window.statusBarColor =
                        ContextCompat.getColor(requireActivity(), R.color.red)
                }
            } else {
                if (Build.VERSION.SDK_INT >= 21) {
                    requireActivity().window.statusBarColor =
                        ContextCompat.getColor(requireActivity(), R.color.overlay_dark_30)
                }
            }
        })
    }

    private fun setShareButton(title: String) {
        binding.btnShare.setOnClickListener {
            val shareMsg = getString(R.string.share_message, title)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareMsg)
            }
            val shareIntent = Intent.createChooser(intent, "Share using...")
            startActivity(shareIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (Build.VERSION.SDK_INT >= 21) {
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.red)
        }
    }

}