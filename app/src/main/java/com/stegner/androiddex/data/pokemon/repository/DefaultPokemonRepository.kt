package com.stegner.androiddex.data.pokemon.repository


import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Error
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.datasource.PokemonDataSource
import com.stegner.androiddex.dependencyinjection.ApplicationModule
import com.stegner.androiddex.util.GET_POKEMON_BY_TYPE_ERROR
import com.stegner.androiddex.util.GET_POKEMON_ERROR
import com.stegner.androiddex.util.GET_POKEMON_LIST_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Single point of access to session data for the ui
 *
 * Dispatchers.IO is a different thread then the main one typically used for background operations
 *
 * withContext turns blocking function into suspended one
 * return@withContext returns the inner call not the overall function
 * I think this is used here because we want to return correctly if success but throw out of the context if not
 * because we failed to get tasks period
 * ex: https://stackoverflow.com/questions/40160489/kotlin-whats-does-return-mean
 */
class DefaultPokemonRepository @Inject constructor(@ApplicationModule.PokemonLocalDataSource private val pokemonLocalDataSource: PokemonDataSource, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): PokemonRepository {

    override suspend fun getPokemon(): Result<List<Pokemon>> {
        return withContext(ioDispatcher) {
            val pokemonFromDatabase = fetchPokemonListFromDataSource()

            // This casts pokemonFromDatabase as Success
            // Same as doing ((Success) pokemonFromDatabase)
            (pokemonFromDatabase as? Success)?.let { list ->
                return@withContext Success(list.data.sortedBy { it.id })
            }

            return@withContext Error(Exception(pokemonFromDatabase.toString()))
        }
    }

    private suspend fun fetchPokemonListFromDataSource(): Result<List<Pokemon>> {
        val pokemon = pokemonLocalDataSource.getPokemon()
        return if(pokemon is Success) {
            pokemon
        }else {
            Error(Exception(GET_POKEMON_LIST_ERROR))
        }
    }

    override suspend fun getPokemon(pokedexId: Int): Result<Pokemon> {

        return withContext(ioDispatcher) {

            return@withContext fetchPokemonFromDataSource(pokedexId)
        }
    }

    private suspend fun fetchPokemonFromDataSource(pokemonId: Int): Result<Pokemon> {
        val pokemon = pokemonLocalDataSource.getPokemon(pokemonId)
        return if(pokemon is Success){
            pokemon
        }else {
            Error(Exception(GET_POKEMON_ERROR))
        }
    }

    override suspend fun getPokemon(type: String): Result<List<Pokemon>> {
        return withContext(ioDispatcher) {
            val pokemonFromDatabase = fetchPokemonListFromDataSourceByType(type, null)

            (pokemonFromDatabase as? Success)?.let {list ->
                return@withContext Success(list.data.sortedBy { it.id })
            }

            return@withContext Error(Exception(pokemonFromDatabase.toString()))
        }
    }

    override suspend fun getPokemon(typeOne: String, typeTwo: String): Result<List<Pokemon>> {
        return withContext(ioDispatcher) {
            val pokemonFromDatabase = fetchPokemonListFromDataSourceByType(typeOne, typeTwo)

            (pokemonFromDatabase as? Success)?.let {list ->
                return@withContext Success(list.data.sortedBy { it.id  })
            }

            return@withContext Error(Exception(pokemonFromDatabase.toString()))
        }
    }

    private suspend fun fetchPokemonListFromDataSourceByType(typeOne: String, typeTwo: String?) : Result<List<Pokemon>>{

        val pokemon: Result<List<Pokemon>> = if(typeTwo != null){
            pokemonLocalDataSource.getPokemon(typeOne, typeTwo)

        }else {
            pokemonLocalDataSource.getPokemon(typeOne)
        }

        return if(pokemon is Success){
            pokemon
        }else {
            Error(Exception(GET_POKEMON_BY_TYPE_ERROR))
        }
    }



}