package id.dimasferians.moviecatalogue.tv.di

import dagger.Component
import id.dimasferians.moviecatalogue.core.di.CoreComponent
import id.dimasferians.moviecatalogue.tv.ui.TvShowFragment

@TvShowScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [TvShowModule::class, TvShowViewModelModule::class]
)
interface TvShowComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): TvShowComponent
    }

    fun inject(fragment: TvShowFragment)

}