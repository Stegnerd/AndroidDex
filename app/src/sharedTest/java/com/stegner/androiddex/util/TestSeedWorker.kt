package com.stegner.androiddex.util

import android.content.Context
import androidx.work.ListenableWorker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import kotlinx.coroutines.coroutineScope

/**
 * Short circuit of DatabaseSeedWorker that can be called outside of WorkManager task
 *
 * Does the same thing but easier to instantiate for unit tests
 */
class TestSeedWorker (private val context: Context, private val database: PokemonDatabase){

    suspend fun seed() = coroutineScope {
        context.assets.open(Constants.Assests.POKEMON_FILE_NAME).use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val pokemonType = object : TypeToken<List<Pokemon>>() {}.type
                val pokemonList: List<Pokemon> = Gson().fromJson(jsonReader, pokemonType)

                database.PokemonDao().seed(pokemonList)

                ListenableWorker.Result.success()
            }
        }
    }
}