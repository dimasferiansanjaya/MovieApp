package id.dimasferians.moviecatalogue.core.ui.tv

import id.dimasferians.moviecatalogue.core.domain.model.TvShow

interface TvShowItemListener {

    fun onItemClicked(tvShow: TvShow)

}