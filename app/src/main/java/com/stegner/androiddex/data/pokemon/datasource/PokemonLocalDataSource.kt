package com.stegner.androiddex.data.pokemon.datasource

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Error
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonDao
import com.stegner.androiddex.util.GET_POKEMON_BY_GENERATION_ERROR
import com.stegner.androiddex.util.GET_POKEMON_BY_TYPE_ERROR
import com.stegner.androiddex.util.GET_POKEMON_ERROR
import com.stegner.androiddex.util.GET_POKEMON_LIST_ERROR
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Concrete implementation of a data source as a db
 */
class PokemonLocalDataSource internal constructor(private val pokemonDao: PokemonDao, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : PokemonDataSource {

    override suspend fun getPokemon(): Result<List<Pokemon>>  = withContext(ioDispatcher){
        return@withContext try {
            Success(pokemonDao.getPokemon())
        }catch (e: Exception){
            Timber.e(e, e.message)
            Error(Exception(GET_POKEMON_LIST_ERROR))
        }
    }

    override suspend fun getPokemon(pokedexId: Int): Result<Pokemon> = withContext(ioDispatcher){
        try {
            val pokemon = pokemonDao.getPokemonById(pokedexId)
            if(pokemon != null){
                return@withContext Success(pokemon)
            }else {
                Timber.e(GET_POKEMON_ERROR)
                return@withContext Error(Exception("$GET_POKEMON_ERROR $pokedexId"))
            }

        } catch (e: Exception){
            Timber.e(e, e.message)
            return@withContext Error(Exception("$GET_POKEMON_ERROR $pokedexId"))
        }
    }

    override suspend fun getPokemonByType(type: String): Result<List<Pokemon>>  = withContext(ioDispatcher){
        try {
            val pokemon = pokemonDao.getPokemonByType(type)
            if(pokemon != null){
                return@withContext Success(pokemon)
            }else {
                Timber.e("$GET_POKEMON_BY_TYPE_ERROR $type")
                return@withContext Error(Exception("$GET_POKEMON_BY_TYPE_ERROR $type"))
            }

        }catch (e: Exception) {
            Timber.e(e, e.message)
            return@withContext Error(e)
        }
    }

    override suspend fun getPokemonByTypes(typeOne: String, typeTwo: String): Result<List<Pokemon>> = withContext(ioDispatcher) {
        try {
            val pokemon = pokemonDao.getPokemonByType(typeOne, typeTwo)
            if(pokemon != null) {
                return@withContext Success(pokemon)
            }else {
                Timber.e("$GET_POKEMON_BY_TYPE_ERROR $typeOne, $typeTwo")
                return@withContext Error(Exception("$GET_POKEMON_BY_TYPE_ERROR $typeOne, $typeTwo"))
            }

        }catch (e: Exception) {
            Timber.e(e, e.message)
            return@withContext Error(Exception("$GET_POKEMON_BY_TYPE_ERROR $typeOne, $typeTwo"))
        }
    }

    override suspend fun getPokemonByGen(generation: Int): Result<List<Pokemon>>  = withContext(ioDispatcher){
        try {
            val pokemon = pokemonDao.getPokemonByGeneration(generation)
            if(pokemon != null){
                return@withContext Success(pokemon)
            }else {
                Timber.e("$GET_POKEMON_BY_GENERATION_ERROR $generation")
                return@withContext Error(Exception("$GET_POKEMON_BY_GENERATION_ERROR $generation"))
            }
        }catch (e: Exception){
            Timber.e(e, e.message)
            return@withContext Error(Exception("$GET_POKEMON_BY_GENERATION_ERROR $generation"))
        }
    }
}