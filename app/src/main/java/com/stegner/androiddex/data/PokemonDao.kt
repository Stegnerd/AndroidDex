package com.stegner.androiddex.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PokemonDao {

    /**
     * Select all pokemon from the Pokemon table.
     *
     * @return All Pokemon.
     */
    @Query("SELECT * FROM Pokemon")
    suspend fun getPokemon(): List<Pokemon>

    /**
     * Select a pokemon by id.
     *
     * @return the pokemon with the given pokedexId
     */
    @Query("SELECT * FROM Pokemon WHERE pokedex_number = :pokedexId")
    suspend fun getPokemonById(pokedexId: Int): Pokemon?

    /**
     * Select a pokemon based on filtering of it's type
     *
     * @return the pokemons with the given types
     */
    @Query("SELECT * FROM Pokemon WHERE type LIKE  :typeOne OR type LIKE :typeTwo")
    suspend fun getPokemonByType(typeOne: String, typeTwo: String?): List<Pokemon>?
}