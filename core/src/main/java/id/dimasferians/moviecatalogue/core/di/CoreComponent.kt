package id.dimasferians.moviecatalogue.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.dimasferians.moviecatalogue.core.domain.repository.MovieRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreRepositoryModule::class])
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideMovieRepository(): MovieRepository

}