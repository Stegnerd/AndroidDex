package com.stegner.androiddex.data.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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
    @Query("SELECT * FROM Pokemon WHERE type LIKE  :typeOne")
    suspend fun getPokemonByType(typeOne: String): List<Pokemon>?

    /**
     * Select a pokemon based on filtering of it's type
     *
     * Used for dual types
     *
     * @return the pokemons with the given types
     */
    @Query("SELECT * FROM pokemon WHERE type LIKE :typeOne AND :typeTwo")
    suspend fun getPokemonByType(typeOne: String, typeTwo: String): List<Pokemon>?

    /**
     * Seeds the database with all pokemon
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun seed(pokemon: List<Pokemon>)

}