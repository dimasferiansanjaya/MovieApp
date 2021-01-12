package id.dimasferians.moviecatalogue.detail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.dimasferians.moviecatalogue.core.di.ViewModelKey
import id.dimasferians.moviecatalogue.core.utils.ViewModelFactory
import id.dimasferians.moviecatalogue.detail.ui.DetailViewModel

@Module
abstract class DetailViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

}