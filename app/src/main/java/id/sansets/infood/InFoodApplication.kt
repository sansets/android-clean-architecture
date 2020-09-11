package id.sansets.infood

import android.app.Application
import id.sansets.infood.core.di.CoreComponent
import id.sansets.infood.core.di.DaggerCoreComponent

class InFoodApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: Application

        val coreComponent: CoreComponent by lazy {
            DaggerCoreComponent.factory().create(instance)
        }
    }
}