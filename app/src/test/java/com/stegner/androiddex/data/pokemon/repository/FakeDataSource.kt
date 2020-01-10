package com.stegner.androiddex.data.pokemon.repository

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Error
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.datasource.PokemonDataSource

/**
 * Stub class for mocking the [DefaultPokemonRepository]
 */
class FakeDataSource(var pokemon: MutableList<Pokemon>? = mutableListOf()) : PokemonDataSource {
    override suspend fun getPokemon(): Result<List<Pokemon>> {
        pokemon?.let {
            return Success(it)
        }

        return Error(Exception("Pokemon not found"))
    }

    override suspend fun getPokemon(pokedexId: Int): Result<Pokemon> {
        pokemon?.firstOrNull { it.id == pokedexId }?.let {
            return Success(it)
        }

        return Error(Exception("Pokemon not found"))
    }

    override suspend fun getPokemonByType(type: String): Result<List<Pokemon>> {
        pokemon?.filter { it.types.contains(type) }?.let {
            return Success(it)
        }

        return Error(Exception("Pokemon not found"))
    }

    override suspend fun getPokemonByTypes(typeOne: String, typeTwo: String): Result<List<Pokemon>> {
        val types = listOf(typeOne, typeTwo)
        pokemon?.filter { it.types.containsAll(types) }?.let {
            return Success(it)
        }

        return Error(Exception("Pokemon not found"))
    }

    override suspend fun getPokemonByGen(generation: Int): Result<List<Pokemon>> {
        pokemon?.filter { it.generation == generation }?.let {
            return Success(it)
        }

        return Error(Exception("Pokemon not found"))
    }

}