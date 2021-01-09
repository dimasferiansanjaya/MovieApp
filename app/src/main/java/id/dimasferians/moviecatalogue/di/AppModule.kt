package id.dimasferians.moviecatalogue.di

import dagger.Binds
import dagger.Module
import id.dimasferians.movieapp.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieInteractor

@Module
abstract class AppUseCaseModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

}