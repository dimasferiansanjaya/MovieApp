package id.dimasferians.moviecatalogue.detail.di

import dagger.Component
import id.dimasferians.moviecatalogue.core.di.CoreComponent
import id.dimasferians.moviecatalogue.detail.ui.DetailFragment

@DetailScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [DetailModule::class, DetailViewModelModule::class]
)
interface DetailComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): DetailComponent
    }

    fun inject(fragment: DetailFragment)

}