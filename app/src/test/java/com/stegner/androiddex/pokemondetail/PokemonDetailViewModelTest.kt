package com.stegner.androiddex.pokemondetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stegner.androiddex.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.stegner.androiddex.LiveDataTestUtil.getValue
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonNames
import com.stegner.androiddex.data.pokemon.PokemonStats
import com.stegner.androiddex.data.pokemon.repository.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Unit tests for the implementation of [PokemonDetailViewModel]
 */
@ExperimentalCoroutinesApi
class PokemonDetailViewModelTest {

    // Subject under test
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var pokemonRepository: FakeRepository

    @ExperimentalCoroutinesApi
    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get: Rule
    var instanceExecutorRule = InstantTaskExecutorRule()

    val names = PokemonNames("Jolteon", "サンダース", "雷伊布", "Voltali")
    val stats = PokemonStats(65, 65, 60, 110, 95, 130)
    val types = listOf("Electric")
    val pokemon = Pokemon(135, 1, names, types, stats)

    @Before
    fun setupViewModel() {
        pokemonRepository = FakeRepository()

        pokemonDetailViewModel = PokemonDetailViewModel(pokemonRepository)

    }

    @Test
    fun getPokemonFromRepositoryAndLoadIntoView() {
        // Start the event
        pokemonDetailViewModel.start(pokemon.id)

        // Then verify that the view was notified
        assertThat(getValue(pokemonDetailViewModel.pokemon).names.english).isEqualTo(pokemon.names.english)
        assertThat(getValue(pokemonDetailViewModel.pokemon).id).isEqualTo(pokemon.id)
        assertThat(getValue(pokemonDetailViewModel.pokemon).types).isEqualTo(pokemon.types)
    }

    @Test
    fun pokemonDetailViewModel_repositoryError() {
        // Given that the repository fails
        pokemonRepository.setReturnError(true)

        // Given an initialized viewmodel with an active task
        pokemonDetailViewModel.start(pokemon.id)

        // Then verify the data is not available
        assertThat(getValue(pokemonDetailViewModel.dataLoading)).isFalse()
    }

    @Test
    fun loadPokemon_loading() {
        // Pause dispather so we can verify initial values
        mainCoroutineRule.pauseDispatcher()

        // Load the task in the viemodel
        pokemonDetailViewModel.start(pokemon.id)

        // The the progress indicator is shown
        assertThat(getValue(pokemonDetailViewModel.dataLoading)).isTrue()

        // Execute pending coroutines actions
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden
        assertThat(getValue(pokemonDetailViewModel.dataLoading)).isFalse()
    }
}