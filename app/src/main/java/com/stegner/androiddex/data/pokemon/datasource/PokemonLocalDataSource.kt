package com.stegner.androiddex.data.pokemon.datasource

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Error
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db
 */
class PokemonLocalDataSource internal constructor(private val pokemonDao: PokemonDao, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : PokemonDataSource {


    override suspend fun getPokemon(): Result<List<Pokemon>>  = withContext(ioDispatcher){
        return@withContext try {
            Success(pokemonDao.getPokemon())
        }catch (e: Exception){
            Error(e)
        }
    }

    override suspend fun getPokemon(pokedexId: Int): Result<Pokemon> = withContext(ioDispatcher){
        try {
            val pokemon = pokemonDao.getPokemonById(pokedexId)
            if(pokemon != null){
                return@withContext Success(pokemon)
            }else {
                return@withContext Error(Exception("Pokemon not found!"))
            }

        } catch (e: Exception){
            return@withContext Error(e)
        }
    }

    override suspend fun getPokemon(type: String): Result<List<Pokemon>>  = withContext(ioDispatcher){
        try {
            val pokemon = pokemonDao.getPokemonByType(type)
            if(pokemon != null){
                return@withContext Success(pokemon)
            }else {
                return@withContext Error(Exception("No pokemon found of that type"))
            }

        }catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun getPokemon(typeOne: String, typeTwo: String): Result<List<Pokemon>> = withContext(ioDispatcher) {
        try {
            val pokemon = pokemonDao.getPokemonByType(typeOne, typeTwo)
            if(pokemon != null) {
                return@withContext Success(pokemon)
            }else {
                return@withContext Error(Exception("No pokemon found of that type combination"))
            }

        }catch (e: Exception) {
            return@withContext Error(e)
        }
    }

}