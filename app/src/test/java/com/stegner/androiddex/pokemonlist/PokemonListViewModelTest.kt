package com.stegner.androiddex.pokemonlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.stegner.androiddex.*
import com.stegner.androiddex.data.pokemon.repository.FakeRepository
import com.stegner.androiddex.util.GenerationFilterType
import com.stegner.androiddex.util.TypeFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [PokemonListViewModel]
 */
@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    private lateinit var pokemonListViewModel: PokemonListViewModel

    private lateinit var pokemonRepository: FakeRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        pokemonRepository = FakeRepository()

        pokemonListViewModel = PokemonListViewModel(pokemonRepository)
    }

    @Test
    fun loadAllPokemonFromRepositoryTogglesLoadingAndDataLoads() {
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()

        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setTypeFiltering(TypeFilter.All, true)

        // Trigger load
        pokemonListViewModel.loadPokemon()

        // Then progress is shown, pause the thread
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isTrue()

        // Execute actions
        mainCoroutineRule.resumeDispatcher()

        // The progress is hidden
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        // data loaded
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(36)
    }

    @Test
    fun loadGenerationFilteredPokemonFromRepositoryAndLoadsIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setGenerationFiltering(GenerationFilterType.KANTO)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(7)
    }

    @Test
    fun loadTypeFilteredPokemonFromRepositoryAndLoadsIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setTypeFiltering(TypeFilter.Water, true)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(4)
    }

    @Test
    fun loadDualTypeFilteredPokemonFromRepositoryAndLoadsIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setTypeFiltering(TypeFilter.Electric, true)
        pokemonListViewModel.setTypeFiltering(TypeFilter.Steel, true)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(1)
    }

    @Test
    fun loadNameFilteredPokemonFromRepositoryAndLoadsIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setNameFiltering("Jolt")

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(1)
    }

    @Test
    fun loadNameFilteredPokemonFromRepositoryReturnsMultipleResultsAndLoadsIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setNameFiltering("Crab")

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(2)
    }

    @Test
    fun loadNameTypeGenerationFilteredPokemonFromRepositoryAndLoadsIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setNameFiltering("Chimchar")
        pokemonListViewModel.setTypeFiltering(TypeFilter.Fire, true)
        pokemonListViewModel.setGenerationFiltering(GenerationFilterType.SINNOH)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(1)
    }

    @Test
    fun loadIncompatibleTypeFilterPokemonFromRepositoryAndLoadsErrorIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setTypeFiltering(TypeFilter.Fire, true)
        pokemonListViewModel.setTypeFiltering(TypeFilter.Water, true)
        pokemonListViewModel.setTypeFiltering(TypeFilter.Electric, true)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).isEmpty()
    }

    @Test
    fun loadNameFilteredNoneFoundAndLoadsEmptyListIntoView(){
        // Given an initialized PokemonListViewModel with initialized pokemon
        // when loading of Pokemon is requested
        pokemonListViewModel.setNameFiltering("NOT_A_POKEMON_NAME")

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).isEmpty()
    }

    @Test
    fun loadPokemon_error(){
        // Make the repository return errors
        pokemonRepository.setReturnError(true)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // Then progress indicator is hidden
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        // And the list of items is empty
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).isEmpty()

        // and snackbar updated
        assertSnackbarMessage(pokemonListViewModel.snackbarMessage, R.string.loading_pokemon_error)
    }

    @Test
    fun clickOnOpenPokemon_setsEvent(){
        // When opening pokemon detail
        val pokedexId = 135
        pokemonListViewModel.openPokemonDetail(pokedexId)

        // Then the event is triggered
        assertLiveDataEventTriggered(pokemonListViewModel.openPokemonEvent, pokedexId)
    }

    @Test
    fun clearTypeFiltersByAddAllLoadsAllPokemon(){
        // Set filter to specific type
        pokemonListViewModel.setTypeFiltering(TypeFilter.Water, true)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        //  and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(4)

        //  Add the all filter to reset the type filter
        pokemonListViewModel.setTypeFiltering(TypeFilter.All, true)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        // and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(36)
    }

    @Test
    fun clearTypeFiltersByUnselectingAllTypesLoadsAllPokemon(){
        // Set filter to specific type
        pokemonListViewModel.setTypeFiltering(TypeFilter.Water, true)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        // and filtered data loaded correctly
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(4)

        // Uncheck filter to no selected types
        pokemonListViewModel.setTypeFiltering(TypeFilter.Water, false)

        // Load pokemon
        pokemonListViewModel.loadPokemon()

        // The progress loader is gone
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        // and pokemon data unloaded
        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(36)
    }
}