package com.stegner.androiddex.pokemongeneration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stegner.androiddex.MainCoroutineRule
import com.stegner.androiddex.assertLiveDataEventTriggered
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonGenerationViewModelTest {

    private lateinit var pokemonGenerationViewModel: PokemonGenerationViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        pokemonGenerationViewModel = PokemonGenerationViewModel()
    }

    @Test
    fun getGenerationSpecificPokemonAndLoadIntoView(){
        // Start the event
        pokemonGenerationViewModel.openPokemonList(1)

        // Then verify the view as notified
        assertLiveDataEventTriggered(pokemonGenerationViewModel.openPokemonGenerationEvent, 1)
    }
}