package com.stegner.androiddex.data.pokemon.repository

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.pokemon.Pokemon

/**
 * Interface to the data layer.
 */
interface PokemonRepository {

    /**
     * Get all [Pokemon] from the database
     */
    suspend fun getPokemon(): Result<List<Pokemon>>

    /**
     * Get a [Pokemon] based on its pokedex id
     *
     * @param pokedexId pokedex number of the pokemon
     */
    suspend fun getPokemon(pokedexId: Int): Result<Pokemon>

    /**
     * Gets a list of [Pokemon] of a specific type
     *
     * @param type The type of pokemon to filter by
     */
    suspend fun getPokemon(type: String): Result<List<Pokemon>>

    /**
     * Gets a list of [Pokemon] of a specific type combination
     *
     * @param typeOne The first type to filter by
     * @param typeTwo The second type to filter by
     */
    suspend fun getPokemon(typeOne:String, typeTwo: String): Result<List<Pokemon>>
}