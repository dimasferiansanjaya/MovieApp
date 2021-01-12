package id.dimasferians.moviecatalogue.movie.di

import dagger.Binds
import dagger.Module
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieInteractor

@Module
abstract class MovieModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}