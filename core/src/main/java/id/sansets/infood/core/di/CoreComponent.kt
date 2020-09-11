package id.sansets.infood.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.sansets.infood.core.domain.repository.ICoreRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CoreRepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository(): ICoreRepository
}