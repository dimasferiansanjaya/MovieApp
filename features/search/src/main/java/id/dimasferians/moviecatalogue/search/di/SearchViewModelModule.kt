package id.dimasferians.moviecatalogue.search.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.dimasferians.moviecatalogue.core.di.ViewModelKey
import id.dimasferians.moviecatalogue.core.utils.ViewModelFactory
import id.dimasferians.moviecatalogue.search.ui.SearchViewModel

@Module
abstract class SearchViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

}