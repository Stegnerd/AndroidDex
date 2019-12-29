package com.stegner.androiddex.dependencyinjection

import androidx.lifecycle.ViewModel
import com.stegner.androiddex.pokemongeneration.PokemonGenerationFragment
import com.stegner.androiddex.pokemongeneration.PokemonGenerationViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PokemonGenerationModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    internal abstract fun pokemonGenerationFragment(): PokemonGenerationFragment

    @Binds
    @IntoMap
    @ViewModelKey(PokemonGenerationViewModel::class)
    abstract fun bindViewModel(viewModel: PokemonGenerationViewModel): ViewModel
}