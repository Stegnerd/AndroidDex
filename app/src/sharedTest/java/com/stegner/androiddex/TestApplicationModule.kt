package com.stegner.androiddex

import android.content.Context
import androidx.room.Room
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import com.stegner.androiddex.data.pokemon.repository.FakeRepository
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * A replacement for [ApplicationModule] to be used in tests. It simply creates a [FakeRepository]
 */
@Module
class TestApplicationModule {

    @Singleton
    @Provides
    fun providesRepository(): PokemonRepository = FakeRepository()

    /**
     * This is needed for dagger to inject into the WorkerModule,
     * The reason https://github.com/android/architecture-samples/tree/dagger-android works without it
     * is there is not a module that injects a db
     */
    @Singleton
    @Provides
    fun providesDatabase(context: Context) : PokemonDatabase {
        return Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
            .allowMainThreadQueries().build()
    }
}
