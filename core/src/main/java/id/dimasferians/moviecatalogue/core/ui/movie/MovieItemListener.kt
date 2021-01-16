package id.dimasferians.moviecatalogue.core.ui.movie

import id.dimasferians.moviecatalogue.core.domain.model.Movie

interface MovieItemListener {

    fun onItemClicked(movie: Movie)

}