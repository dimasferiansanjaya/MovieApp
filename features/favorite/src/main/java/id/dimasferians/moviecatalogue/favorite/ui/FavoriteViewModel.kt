package id.dimasferians.moviecatalogue.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import id.dimasferians.movieapp.core.domain.usecase.MovieUseCase

class FavoriteViewModel @ViewModelInject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val favoriteMovie = movieUseCase.getFavoriteMovie()

    val favoriteTvShow = movieUseCase.getFavoriteTvShow()

}