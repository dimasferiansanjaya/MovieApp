package id.dimasferians.moviecatalogue.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.dimasferians.moviecatalogue.core.di.ViewModelKey
import id.dimasferians.moviecatalogue.core.utils.ViewModelFactory
import id.dimasferians.moviecatalogue.favorite.ui.FavoriteViewModel

@Module
abstract class FavoriteViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(favoriteViewModel: FavoriteViewModel): ViewModel
}