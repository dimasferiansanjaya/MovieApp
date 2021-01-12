package id.dimasferians.moviecatalogue.detail.di

import dagger.Binds
import dagger.Module
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieInteractor

@Module
abstract class DetailModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

}