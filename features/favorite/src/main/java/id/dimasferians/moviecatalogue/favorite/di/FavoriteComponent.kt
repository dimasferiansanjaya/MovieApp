package id.dimasferians.moviecatalogue.favorite.di

import dagger.Component
import id.dimasferians.moviecatalogue.core.di.CoreComponent
import id.dimasferians.moviecatalogue.favorite.ui.FavoriteMovieFragment
import id.dimasferians.moviecatalogue.favorite.ui.FavoriteTvFragment

@FavoriteScope
@Component(
    dependencies = [CoreComponent::class],
    modules =[FavoriteModule::class, FavoriteViewModelModule::class]
)
interface FavoriteComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): FavoriteComponent
    }

    fun inject(fragment: FavoriteMovieFragment)
    fun inject(fragment: FavoriteTvFragment)

}