package id.dimasferians.moviecatalogue.search.di

import dagger.Binds
import dagger.Module
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieInteractor

@Module
abstract class SearchModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

}