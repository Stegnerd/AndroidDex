package com.stegner.androiddex.data.pokemon.repository

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Error
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.util.Constants.Errors.GET_POKEMON_BY_GENERATION_ERROR
import com.stegner.androiddex.util.Constants.Errors.GET_POKEMON_BY_TYPE_ERROR
import com.stegner.androiddex.util.Constants.Errors.GET_POKEMON_ERROR
import com.stegner.androiddex.util.Constants.Errors.GET_POKEMON_LIST_ERROR
import com.stegner.androiddex.util.MockData

/**
 * Implementation of a remote data source with static access to the data for easy testing
 */
class FakeRepository : PokemonRepository {

    // Temporary holder of data during tests
    private var pokemonData: LinkedHashMap<Int, Pokemon> = LinkedHashMap()

    private var shouldReturnError = false

    init {
        // Seed  the database for mock data
        seedPokemon()
    }


    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getPokemon(): Result<List<Pokemon>> {
        if(shouldReturnError){
            return Error(Exception(GET_POKEMON_LIST_ERROR))
        }
        return Success(pokemonData.values.toList())
    }

    override suspend fun getPokemon(pokedexId: Int): Result<Pokemon> {
        if(shouldReturnError){
            return Error(Exception("$GET_POKEMON_ERROR $pokedexId"))
        }
        pokemonData[pokedexId]?.let {
            return Success(it)
        }
        return Error(Exception("$GET_POKEMON_ERROR $pokedexId"))
    }

    override suspend fun getPokemonByType(type: String): Result<List<Pokemon>> {
        if(shouldReturnError){
            return Error(Exception("$GET_POKEMON_BY_TYPE_ERROR $type"))
        }
        val filter = pokemonData.values.filter {
            it.types.contains(type)
        }

        return when(filter.count()) {
            0 -> Error(Exception("$GET_POKEMON_BY_TYPE_ERROR $type"))
            else -> Success(filter)
        }
    }

    override suspend fun getPokemonByTypes(typeOne: String, typeTwo: String): Result<List<Pokemon>> {
        val types = mutableListOf(typeOne, typeTwo)
        if(shouldReturnError){
            return Error(Exception("$GET_POKEMON_BY_TYPE_ERROR $typeOne, $typeTwo"))
        }
        val filter =  pokemonData.values.filter {
            it.types.containsAll(types)
        }

        return when(filter.count()){
            0 -> Error(Exception("$GET_POKEMON_BY_TYPE_ERROR $typeOne, $typeTwo"))
            else -> Success(filter)
        }
    }

    override suspend fun getPokemonByGeneration(generation: Int): Result<List<Pokemon>> {
        if(shouldReturnError){
            return Error(Exception("$GET_POKEMON_BY_GENERATION_ERROR $generation"))
        }
        val filter = pokemonData.values.filter {
            it.generation == generation
        }

        return when(filter.count()){
            0 -> Error(Exception("$GET_POKEMON_BY_GENERATION_ERROR $generation"))
            else -> Success(filter)
        }
    }

    fun seedPokemon(){
        val seedData = MockData.generateData()

        for (poke in seedData){
            pokemonData[poke.id] = poke
        }
    }
}