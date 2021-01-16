package id.dimasferians.moviecatalogue.core.ui.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.dimasferians.moviecatalogue.core.BuildConfig
import id.dimasferians.moviecatalogue.core.R
import id.dimasferians.moviecatalogue.core.databinding.ItemMovieTvBinding
import id.dimasferians.moviecatalogue.core.domain.model.TvShow

class TvShowPagingAdapter(private val tvShowItemListener: TvShowItemListener) :
    PagingDataAdapter<TvShow, TvShowPagingAdapter.TvShowViewHolder>(TV_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = ItemMovieTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class TvShowViewHolder(private val itemBinding: ItemMovieTvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(tvShow: TvShow) {
            // i have tested that poster_path and overview in some case return null/blank
            Glide.with(itemBinding.container.context)
                .load(
                    if (tvShow.image == null) R.drawable.cover_placeholder else
                        BuildConfig.BASE_IMAGE_URL + tvShow.image
                )
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.cover_placeholder)
                        .transform(RoundedCorners(20))
                )
                .into(itemBinding.imgCover)

            with(itemBinding) {
                tvOverview.text =
                    if (tvShow.overview != "") tvShow.overview else itemBinding.container.context.getString(
                        R.string.no_overview
                    )
                tvTitle.text = tvShow.name
                ratingBar.rating = tvShow.voteAverage.div(2).toFloat()
                tvVoteCount.text =
                    itemBinding.container.context.getString(R.string.vote_count, tvShow.voteCount)
            }

            itemView.setOnClickListener { tvShowItemListener.onItemClicked(tvShow) }
        }
    }

    companion object {
        private val TV_COMPARATOR = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                // id is unique
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem == newItem

        }
    }

}