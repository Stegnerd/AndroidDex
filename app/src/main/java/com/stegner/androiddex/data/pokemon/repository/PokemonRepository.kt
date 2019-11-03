package com.stegner.androiddex.data.pokemon.repository

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.pokemon.Pokemon

/**
 * Interface to the data layer.
 */
interface PokemonRepository {

    suspend fun getPokemon(): Result<List<Pokemon>>

    suspend fun getPokemon(pokedexId: Int): Result<Pokemon>

    suspend fun getPokemon(type: String): Result<List<Pokemon>>

    suspend fun getPokemon(typeOne:String, typeTwo: String): Result<List<Pokemon>>
}