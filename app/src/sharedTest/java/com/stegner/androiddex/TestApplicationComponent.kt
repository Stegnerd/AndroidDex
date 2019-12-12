package com.stegner.androiddex

import android.content.Context
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import com.stegner.androiddex.dependencyinjection.PokemonListModule
import com.stegner.androiddex.dependencyinjection.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Test instance of Dagger Component
 */
@Singleton
@Component(modules = [
        TestApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        PokemonListModule::class])
interface TestApplicationComponent : AndroidInjector<TestAndroiddexApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestApplicationComponent
    }

    val pokemonRepository: PokemonRepository
}