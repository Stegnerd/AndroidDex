package com.stegner.androiddex.util

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

/**
 * Worker class that is used to seed the database on create
 */
class SeedDatabaseWorker @Inject constructor (private val database: PokemonDatabase, context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    // just an instance of this classes name for logging purposes
    private val TAG by lazy {SeedDatabaseWorker::class.java.simpleName}

    /**
     * This method takes the json file and creates Entities out of it and then inserts them into the
     *  database for use
     */
    override suspend fun doWork(): Result  = coroutineScope{
        try {
            applicationContext.assets.open(POKEMON_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val pokemonType = object : TypeToken<List<Pokemon>>() {}.type
                    val pokemonList: List<Pokemon> = Gson().fromJson(jsonReader, pokemonType)

                    database.PokemonDao().seed(pokemonList)

                    Log.i(TAG, "Successfully seeded the database")

                    Result.success()
                }
            }
        }catch (e: Exception){
            Log.i(TAG, "Failed seed the database")
            Result.failure()
        }
    }

}