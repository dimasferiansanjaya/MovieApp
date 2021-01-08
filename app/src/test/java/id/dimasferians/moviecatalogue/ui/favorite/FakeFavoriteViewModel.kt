package id.dimasferians.moviecatalogue.ui.favorite

import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl

class FakeFavoriteViewModel(private val repository: MovieRepositoryImpl) {

    val favoriteMoviePagingData
        get() = repository.getFavoriteMovie()

    val favoriteTvShowPagingData
        get() = repository.getFavoriteTvShow()

}