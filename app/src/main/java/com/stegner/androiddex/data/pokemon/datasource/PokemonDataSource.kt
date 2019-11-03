package com.stegner.androiddex.data.pokemon.datasource

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.pokemon.Pokemon

/**
 * Main entry point for accessing [Pokemon] data.
 */
interface PokemonDataSource {

    suspend fun getPokemon(): Result<List<Pokemon>>

    suspend fun getPokemon(pokedexId: Int): Result<Pokemon>

    suspend fun getPokemon(type: String): Result<List<Pokemon>>

    suspend fun getPokemon(typeOne: String, typeTwo: String): Result<List<Pokemon>>
}