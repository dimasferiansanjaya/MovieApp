package id.dimasferians.moviecatalogue.ui.movie

import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl

class FakeMovieViewModel(private val repository: MovieRepositoryImpl) {

    val moviePagingData
        get() = repository.getRemotePagingMovie()

}