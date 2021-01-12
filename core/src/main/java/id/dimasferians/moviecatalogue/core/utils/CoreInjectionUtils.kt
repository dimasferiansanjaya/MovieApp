package id.dimasferians.moviecatalogue.core.utils

import android.content.Context
import androidx.fragment.app.Fragment
import id.dimasferians.moviecatalogue.core.di.CoreComponent
import id.dimasferians.moviecatalogue.core.di.CoreComponentProvider

fun Fragment.provideCoreComponent(): CoreComponent {
    return provideCoreComponent(requireActivity().application)
}

private fun provideCoreComponent(applicationContext: Context): CoreComponent {
    return if (applicationContext is CoreComponentProvider) {
        (applicationContext as CoreComponentProvider).provideCoreComponent()
    } else {
        throw IllegalStateException("The application context you have passed does not implement CoreComponentProvider")
    }
}