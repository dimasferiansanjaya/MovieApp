package id.dimasferians.moviecatalogue.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.dimasferians.movieapp.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class TvShowViewModel @Inject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val tvShowPagingData = movieUseCase.getPopularTvShow().cachedIn(viewModelScope)

}