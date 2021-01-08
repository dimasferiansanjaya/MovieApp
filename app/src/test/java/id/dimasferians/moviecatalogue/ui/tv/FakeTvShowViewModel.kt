package id.dimasferians.moviecatalogue.ui.tv

import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl

class FakeTvShowViewModel(private val repository: MovieRepositoryImpl) {

    val pagingDataTv
        get() = repository.getRemotePagingTvShow()

}