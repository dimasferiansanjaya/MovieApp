package id.dimasferians.moviecatalogue

import android.app.Application
import id.dimasferians.moviecatalogue.core.di.CoreComponent
import id.dimasferians.moviecatalogue.core.di.CoreComponentProvider
import id.dimasferians.moviecatalogue.core.di.DaggerCoreComponent
import timber.log.Timber

class MovieApp : Application(), CoreComponentProvider {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: Application

        val coreComponent: CoreComponent by lazy {
            DaggerCoreComponent.factory().create(instance)
        }
    }

    override fun provideCoreComponent(): CoreComponent {
        return coreComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}