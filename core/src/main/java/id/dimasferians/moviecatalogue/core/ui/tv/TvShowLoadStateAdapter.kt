package id.dimasferians.moviecatalogue.core.ui.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.dimasferians.moviecatalogue.core.R
import id.dimasferians.moviecatalogue.core.databinding.LoadStateFooterBinding

class TvShowLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<TvShowLoadStateAdapter.TvShowLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: TvShowLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): TvShowLoadStateViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.load_state_footer, parent, false)
        return TvShowLoadStateViewHolder(inflater, retry)
    }

    inner class TvShowLoadStateViewHolder(
        itemView: View,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        val binding = LoadStateFooterBinding.bind(itemView)

        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.errorMsg.isVisible = loadState !is LoadState.Loading
        }
    }

}