package com.stegner.androiddex.pokemonlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.stegner.androiddex.LiveDataTestUtil
import com.stegner.androiddex.MainCoroutineRule
import com.stegner.androiddex.data.pokemon.repository.FakeRepository
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import com.stegner.androiddex.util.TypeFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    private lateinit var pokemonListViewModel: PokemonListViewModel

    private lateinit var pokemonRepository: PokemonRepository

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
    fun loadAllPokemonFromRepository_TogglesLoadingAndDataLoads() {

        mainCoroutineRule.pauseDispatcher()

        pokemonListViewModel.setTypeFiltering(TypeFilter.All, true)

        pokemonListViewModel.loadPokemon()

        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isTrue()

        mainCoroutineRule.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.dataLoading)).isFalse()

        assertThat(LiveDataTestUtil.getValue(pokemonListViewModel.items)).hasSize(809)
    }
}