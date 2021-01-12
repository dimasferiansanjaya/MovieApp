package id.dimasferians.moviecatalogue.core.di

import dagger.Binds
import dagger.Module
import id.dimasferians.moviecatalogue.core.data.MovieRepositoryImpl
import id.dimasferians.moviecatalogue.core.domain.repository.MovieRepository

@Module(includes = [CoreNetworkModule::class, CoreDatabaseModule::class, CoreModule::class])
abstract class CoreRepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}