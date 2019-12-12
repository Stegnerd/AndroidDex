package com.stegner.androiddex

import com.stegner.androiddex.data.pokemon.repository.FakeRepository
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * A replacement for [ApplicationModule] to be used in tests. It simply creates a [FakeRepository]
 */
@Module
class TestApplicationModule {

    @Singleton
    @Provides
    fun providesRepository(): PokemonRepository = FakeRepository()
}