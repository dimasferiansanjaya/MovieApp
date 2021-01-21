package id.dimasferians.moviecatalogue.favorite.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.BuildConfig
import id.dimasferians.moviecatalogue.core.databinding.ItemMovieTvBinding
import id.dimasferians.moviecatalogue.core.domain.model.Movie
import id.dimasferians.moviecatalogue.core.ui.movie.MovieItemListener

class FavoriteMovieAdapter(private val movieItemListener: MovieItemListener) :
    RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {

    private var movieList: List<Movie> = emptyList()

    fun setData(movieList: List<Movie>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    inner class ViewHolder(private val itemBinding: ItemMovieTvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movie) {
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
                    if (movie.overview != "") movie.overview else itemBinding.container.context.getString(R.string.no_overview)
                tvTitle.text = movie.title
                ratingBar.rating = movie.voteAverage.div(2).toFloat()
                tvVoteCount.text = itemBinding.container.context.getString(R.string.vote_count, movie.voteCount)
            }
            itemView.setOnClickListener { movieItemListener.onItemClicked(movie) }
        }
    }
}