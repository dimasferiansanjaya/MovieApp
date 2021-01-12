package id.dimasferians.moviecatalogue.favorite.di

import dagger.Binds
import dagger.Module
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieUseCase
import id.dimasferians.moviecatalogue.core.domain.usecase.MovieInteractor

@Module
abstract class FavoriteModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}