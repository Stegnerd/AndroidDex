package com.stegner.androiddex.dependencyinjection

import androidx.lifecycle.ViewModel
import com.stegner.androiddex.pokemonlist.PokemonListFragment
import com.stegner.androiddex.pokemonlist.PokemonListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for the pokemon list feature.
 */
@Module
abstract class PokemonListModule {

    /**
     * @ContributesAndroidInjector - has to be used when inside a module and returns a concrete framework type
 *                                  like activity. Creates it as a sub component
     */
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    internal abstract fun pokemonListFragment(): PokemonListFragment

    /**
     * inject this object into a Map ( @IntoMap annotation) using PokemonListViewModel.class as key,
     * and a Provider that will build a PokemonListViewModel object (the parameter of the @Binds annotation) as value
     */
    @Binds
    @IntoMap
    @ViewModelKey(PokemonListViewModel::class)
    abstract fun bindViewModel(viewModel: PokemonListViewModel): ViewModel
}