package id.dimasferians.moviecatalogue.tv.di

import dagger.Binds
import dagger.Module
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieInteractor

@Module
abstract class TvShowModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

}