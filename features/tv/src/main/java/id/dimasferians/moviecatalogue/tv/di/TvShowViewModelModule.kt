package id.dimasferians.moviecatalogue.tv.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.dimasferians.moviecatalogue.core.di.ViewModelKey
import id.dimasferians.moviecatalogue.core.utils.ViewModelFactory
import id.dimasferians.moviecatalogue.tv.ui.TvShowViewModel

@Module
abstract class TvShowViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TvShowViewModel::class)
    abstract fun bindTvShowViewModel(tvShowViewModel: TvShowViewModel): ViewModel

}