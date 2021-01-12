package id.dimasferians.moviecatalogue.favorite.ui

import androidx.lifecycle.ViewModel
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.favorite.di.FavoriteScope
import javax.inject.Inject

@FavoriteScope
class FavoriteViewModel @Inject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val favoriteMovie = movieUseCase.getFavoriteMovie()

    val favoriteTvShow = movieUseCase.getFavoriteTvShow()

}