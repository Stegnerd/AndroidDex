package com.stegner.androiddex.data.pokemon.repository


import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Error
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.datasource.PokemonDataSource
import com.stegner.androiddex.dependencyinjection.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
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

    // just an instance of this classes name for logging purposes
    private val TAG by lazy { DefaultPokemonRepository::class.java.simpleName}

    override suspend fun getPokemon(): Result<List<Pokemon>> {
        return withContext(ioDispatcher) {
            val pokemonFromDatabase = fetchPokemonListFromDataSource()

            // This casts pokemonFromDatabase as Success
            // Same as doing ((Success) pokemonFromDatabase)
            (pokemonFromDatabase as? Success)?.let { list ->
                return@withContext Success(list.data.sortedBy { it.id })
            }

            return@withContext pokemonFromDatabase
        }
    }

    private suspend fun fetchPokemonListFromDataSource(): Result<List<Pokemon>> {
        val pokemon = pokemonLocalDataSource.getPokemon()

        return when (pokemon){
            is Success -> pokemon
            is Error -> {
                Timber.e(TAG,"Failed to load all data from local data store.")
                Error(Exception(pokemon.exception))
            }
            else -> throw IllegalStateException()
        }
    }

    override suspend fun getPokemon(pokedexId: Int): Result<Pokemon> {

        return withContext(ioDispatcher) {
            return@withContext fetchPokemonFromDataSource(pokedexId)
        }
    }

    private suspend fun fetchPokemonFromDataSource(pokemonId: Int): Result<Pokemon> {
        val pokemon = pokemonLocalDataSource.getPokemon(pokemonId)
        return when(pokemon) {
            is Success -> pokemon
            is Error -> {
                Timber.e(TAG, "Failed to load data from local data store by id.")
                Error(Exception(pokemon.exception))
            }
            else -> throw IllegalStateException()
        }
    }

    override suspend fun getPokemonByType(type: String): Result<List<Pokemon>> {
        return withContext(ioDispatcher) {
            val pokemonFromDatabase = fetchPokemonListFromDataSourceByType(type, null)

            (pokemonFromDatabase as? Success)?.let {list ->
                return@withContext Success(list.data.sortedBy { it.id })
            }

            return@withContext pokemonFromDatabase
        }
    }

    override suspend fun getPokemonByTypes(typeOne: String, typeTwo: String): Result<List<Pokemon>> {
        return withContext(ioDispatcher) {
            val pokemonFromDatabase = fetchPokemonListFromDataSourceByType(typeOne, typeTwo)

            (pokemonFromDatabase as? Success)?.let {list ->
                return@withContext Success(list.data.sortedBy { it.id  })
            }

            return@withContext pokemonFromDatabase
        }
    }

    private suspend fun fetchPokemonListFromDataSourceByType(typeOne: String, typeTwo: String?) : Result<List<Pokemon>>{

        val pokemon: Result<List<Pokemon>> = if(typeTwo != null){
            pokemonLocalDataSource.getPokemonByTypes(typeOne, typeTwo)
        }else {
            pokemonLocalDataSource.getPokemonByType(typeOne)
        }

        return when(pokemon) {
            is Success -> pokemon
            is Error -> {
                Timber.e(TAG, "Failed to load data from local data store by type.")
                Error(Exception(pokemon.exception))
            }
            else -> throw IllegalStateException()
        }
    }

    override suspend fun getPokemonByGeneration(generation: Int): Result<List<Pokemon>> {
        return withContext(ioDispatcher) {
            val pokemonFromDatabase = fetchPokemonListFRomDataSourceByGeneration(generation)

            (pokemonFromDatabase as? Success)?.let { list ->
                return@withContext Success(list.data.sortedBy { it.id })
            }

            return@withContext pokemonFromDatabase
        }
    }

    private suspend fun fetchPokemonListFRomDataSourceByGeneration(generation: Int): Result<List<Pokemon>>{
        val pokemon = pokemonLocalDataSource.getPokemonByGen(generation)

        return when(pokemon) {
            is Success -> pokemon
            is Error -> {
                Timber.e(TAG, "Failed to load data from local data store by generation.")
                Error(Exception(pokemon.exception))
            }
            else -> throw IllegalStateException()
        }
    }

}