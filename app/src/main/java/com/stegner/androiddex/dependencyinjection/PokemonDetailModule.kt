package com.stegner.androiddex.dependencyinjection

import androidx.lifecycle.ViewModel
import com.stegner.androiddex.pokemondetail.PokemonDetailFragment
import com.stegner.androiddex.pokemondetail.PokemonDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PokemonDetailModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    internal abstract fun pokemonDetailFragment(): PokemonDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(PokemonDetailViewModel::class)
    abstract fun bindViewModel(viewmodel: PokemonDetailViewModel): ViewModel
}