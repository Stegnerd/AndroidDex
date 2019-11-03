package com.stegner.androiddex.data.pokemon.repository

import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.datasource.PokemonDataSource
import com.stegner.androiddex.dependencyinjection.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultPokemonRepository @Inject constructor(@ApplicationModule.PokemonLocalDataSource private val pokemonLocalDataSource: PokemonDataSource, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): PokemonRepository {
    override suspend fun getPokemon(): Result<List<Pokemon>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getPokemon(pokedexId: Int): Result<Pokemon> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getPokemon(type: String): Result<List<Pokemon>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getPokemon(typeOne: String, typeTwo: String): Result<List<Pokemon>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}