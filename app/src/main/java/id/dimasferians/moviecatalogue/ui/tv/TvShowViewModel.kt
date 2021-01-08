package id.dimasferians.moviecatalogue.ui.tv

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.dimasferians.movieapp.core.domain.usecase.MovieUseCase

class TvShowViewModel @ViewModelInject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val tvShowPagingData = movieUseCase.getPopularTvShow().cachedIn(viewModelScope)

}