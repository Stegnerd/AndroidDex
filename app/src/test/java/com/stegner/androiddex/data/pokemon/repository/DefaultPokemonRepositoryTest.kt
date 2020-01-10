package com.stegner.androiddex.data.pokemon.repository

import com.google.common.truth.Truth.assertThat
import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.util.MockData
import com.stegner.androiddex.util.TypeFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the implementation of the in-memory repository
 *
 * Current issue with runblockingtest:
 * https://github.com/Kotlin/kotlinx.coroutines/issues/1204
 *
 *
 */
@ExperimentalCoroutinesApi
class DefaultPokemonRepositoryTest {

    private var pokemonList = MockData.generateData()
    private lateinit var pokemonLocalDataSource: FakeDataSource

    // Class under test
    private lateinit var pokemonRepository: DefaultPokemonRepository

    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        pokemonLocalDataSource = FakeDataSource(pokemonList.toMutableList())

        pokemonRepository = DefaultPokemonRepository(pokemonLocalDataSource)
    }

    @Test
    fun getPokemon_requestsAllPokemonFromLocalDataSource() = runBlocking {
        // Trigger the repository to load data, which loads from local
        val pokemon = pokemonRepository.getPokemon() as Success

        // The pokemon are loaded from the local data source
        assertThat(pokemon.data).isEqualTo(pokemonList.sortedBy { it.id })
    }

    @Test
    fun getPokemon_localUnavailable_error() = runBlocking {
        // Make local data source unavailable
        pokemonLocalDataSource.pokemon = null

        // Load pokemon
        val pokemon = pokemonRepository.getPokemon()

        // Result is an error
        assertThat(pokemon).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun getPokemonById_requestPokemonByIdFromLocalDataSource() = runBlocking {
        // Trigger the repository to load data, which loads from local
        val pokemon = pokemonRepository.getPokemon(135) as Success

        // The specific pokemon is loaded from the local data source
        assertThat(pokemon.data.id).isEqualTo(135)
    }

    @Test
    fun getPokemonById_localUnavailable_Error() = runBlocking {
        // Make local data source unavailable
        pokemonLocalDataSource.pokemon = null

        // Load pokemon
        val pokemon = pokemonRepository.getPokemon(135)

        // Result is an error
        assertThat(pokemon).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun getPokemonByType_requestPokemonByTypeFromLocalDataSource() = runBlocking {
        // Trigger the repository to load data, which loads from local
        val pokemon = pokemonRepository.getPokemonByType(TypeFilter.Water.toString()) as Success

        // Pokemon of the specific type are loaded from local data source
        assertThat(pokemon.data.count()).isEqualTo(4)
    }

    @Test
    fun getPokemonByType_localUnavailable_Error() = runBlocking {
        // Make local data source unavailable
        pokemonLocalDataSource.pokemon = null

        // Load pokemon
        val pokemon = pokemonRepository.getPokemonByType(TypeFilter.Water.toString())

        // result is an error
        assertThat(pokemon).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun getPokemonByTypes_requestPokemonByTypeFromLocalDataSource() = runBlocking {
        // Trigger the repository to load data, which loads from local
        val pokemon = pokemonRepository.getPokemonByTypes(TypeFilter.Water.toString(), TypeFilter.Normal.toString()) as Success

        // Pokemon of the specific type combination are loaded from local data sources
        assertThat(pokemon.data.count()).isEqualTo(1)
    }

    @Test
    fun getPokemonByTypes_localUnavailable_Error() = runBlocking {
        // Make local data source unavailable
        pokemonLocalDataSource.pokemon = null

        // Load pokemon
        val pokemon = pokemonRepository.getPokemonByTypes(TypeFilter.Water.toString(), TypeFilter.Normal.toString())

        // result is an error
        assertThat(pokemon).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun getPokemonByGeneration_requestPokemonByGenerationFromLocalDataSource() = runBlocking {
        // Trigger the repository to load data, which loads from local
        val pokemon = pokemonRepository.getPokemonByGeneration(1) as Success

        // Pokemon of the specific generation are loaded from local data source
        assertThat(pokemon.data.count()).isEqualTo(7)
    }

    @Test
    fun getPokemonByGeneration_localUnavailable_error() = runBlocking {
        // make local data source unavailable
        pokemonLocalDataSource.pokemon = null

        // Load pokemon
        val pokemon = pokemonRepository.getPokemonByGeneration(1)

        // Result is an error
        assertThat(pokemon).isInstanceOf(Result.Error::class.java)
    }
}