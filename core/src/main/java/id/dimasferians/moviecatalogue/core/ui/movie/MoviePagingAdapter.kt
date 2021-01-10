package id.dimasferians.moviecatalogue.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.dimasferians.moviecatalogue.BuildConfig
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.databinding.ItemMovieTvBinding
import id.dimasferians.moviecatalogue.core.domain.model.Movie

class MoviePagingAdapter(private val onItemClicked: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onItemClicked)
        }
    }

    class MovieViewHolder(private val itemBinding: ItemMovieTvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movie: Movie, onItemClicked: (Movie) -> Unit) {
            // i have tested that poster_path and overview in some case return null/blank
            Glide.with(itemBinding.container.context)
                .load(
                    if (movie.image == null) R.drawable.cover_placeholder else
                        BuildConfig.BASE_IMAGE_URL + movie.image
                )
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.cover_placeholder)
                        .transform(RoundedCorners(20))
                )
                .into(itemBinding.imgCover)

            with(itemBinding) {
                tvOverview.text =
                    if (movie.overview != "") movie.overview else itemBinding.container.context.getString(
                        R.string.no_overview
                    )
                tvTitle.text = movie.title
                ratingBar.rating = movie.voteAverage.div(2).toFloat()
                tvVoteCount.text =
                    itemBinding.container.context.getString(R.string.vote_count, movie.voteCount)
            }
            itemView.setOnClickListener { onItemClicked(movie) }
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                // id is unique
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }

}