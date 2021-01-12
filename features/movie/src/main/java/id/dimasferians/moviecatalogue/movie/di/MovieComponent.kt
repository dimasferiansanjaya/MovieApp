package id.dimasferians.moviecatalogue.movie.di

import dagger.Component
import id.dimasferians.moviecatalogue.core.di.CoreComponent
import id.dimasferians.moviecatalogue.movie.ui.MovieFragment

@MovieScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [MovieModule::class, MovieViewModelModule::class]
)
interface MovieComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): MovieComponent
    }

    fun inject(fragment: MovieFragment)

}