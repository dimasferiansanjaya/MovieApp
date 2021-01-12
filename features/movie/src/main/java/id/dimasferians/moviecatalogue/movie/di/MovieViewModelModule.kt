package id.dimasferians.moviecatalogue.movie.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.dimasferians.moviecatalogue.core.di.ViewModelKey
import id.dimasferians.moviecatalogue.core.utils.ViewModelFactory
import id.dimasferians.moviecatalogue.movie.ui.MovieViewModel

@Module
abstract class MovieViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel
}