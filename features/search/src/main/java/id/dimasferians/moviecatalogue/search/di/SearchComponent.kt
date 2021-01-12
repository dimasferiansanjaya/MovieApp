package id.dimasferians.moviecatalogue.search.di

import dagger.Component
import id.dimasferians.moviecatalogue.core.di.CoreComponent
import id.dimasferians.moviecatalogue.search.ui.SearchFragment
import id.dimasferians.moviecatalogue.search.ui.SearchMovieFragment
import id.dimasferians.moviecatalogue.search.ui.SearchTvFragment

@SearchScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [SearchModule::class, SearchViewModelModule::class]
)
interface SearchComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): SearchComponent
    }

    fun inject(fragment: SearchFragment)
    fun inject(fragment: SearchMovieFragment)
    fun inject(fragment: SearchTvFragment)
}