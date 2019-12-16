package com.stegner.androiddex.dependencyinjection.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import com.stegner.androiddex.util.Constants.Assests.POKEMON_FILE_NAME
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * Worker class that is used to seed the database on create
 *
 * CoroutineWorker is an implementation of ListenableWorker
 */
class DataBaseSeedWorker (context: Context, params: WorkerParameters, private val database: PokemonDatabase): CoroutineWorker(context, params) {
    // just an instance of this classes name for logging purposes
    private val TAG by lazy { DataBaseSeedWorker::class.java.simpleName}

    /**
     * This method takes the json file and creates Entities out of it and then inserts them into the
     *  database for use
     */
    override suspend fun doWork():  Result  = coroutineScope {
        try {
            applicationContext.assets.open(POKEMON_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val pokemonType = object : TypeToken<List<Pokemon>>() {}.type
                    val pokemonList: List<Pokemon> = Gson().fromJson(jsonReader, pokemonType)

                    database.PokemonDao().seed(pokemonList)

                    Result.success()
                }
            }
        } catch (e: Exception) {
            Log.i(TAG, "Failed seed the database")
            Result.failure()
        }
    }

    /**
     * Factory method for injection with dagger. This will be called by DaggerAwareWorkerFactory to inject the instance
     * of [DataBaseSeedWorker] when it is needed
     */
    class Factory @Inject constructor(private val context: Provider<Context>, private val database: Provider<PokemonDatabase>): IWorkerFactory<DataBaseSeedWorker> {
        override fun create(params: WorkerParameters): DataBaseSeedWorker {
            return DataBaseSeedWorker(context.get(), params, database.get())
        }

    }
}