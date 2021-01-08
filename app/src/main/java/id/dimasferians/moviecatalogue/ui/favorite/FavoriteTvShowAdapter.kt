package id.dimasferians.moviecatalogue.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import id.dimasferians.moviecatalogue.BuildConfig
import id.dimasferians.moviecatalogue.R
import id.dimasferians.moviecatalogue.core.domain.model.TvShow
import id.dimasferians.moviecatalogue.databinding.ItemMovieTvBinding

class FavoriteTvShowAdapter(private val onItemClicked: (TvShow) -> Unit) :
    RecyclerView.Adapter<FavoriteTvShowAdapter.ViewHolder>() {

    private var tvShowList: List<TvShow> = emptyList()

    fun setData(tvShowList: List<TvShow>) {
        this.tvShowList = tvShowList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvShowList[position], onItemClicked)
    }

    override fun getItemCount(): Int = tvShowList.size

    class ViewHolder(private val itemBinding: ItemMovieTvBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(tvShow: TvShow, onItemClicked: (TvShow) -> Unit) {
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

            itemView.setOnClickListener { onItemClicked(tvShow) }
        }

    }
}