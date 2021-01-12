package id.dimasferians.moviecatalogue.tv.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.tv.di.TvShowScope
import javax.inject.Inject

@TvShowScope
class TvShowViewModel @Inject constructor(movieUseCase: MovieUseCase) :
    ViewModel() {

    val tvShowPagingData = movieUseCase.getPopularTvShow().cachedIn(viewModelScope)

}