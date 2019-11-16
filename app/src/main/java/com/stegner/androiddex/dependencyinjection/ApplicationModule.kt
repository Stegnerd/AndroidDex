package com.stegner.androiddex.dependencyinjection

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import com.stegner.androiddex.data.pokemon.datasource.PokemonDataSource
import com.stegner.androiddex.data.pokemon.datasource.PokemonLocalDataSource
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import com.stegner.androiddex.data.pokemon.repository.DefaultPokemonRepository
import com.stegner.androiddex.util.SeedDatabaseWorker
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Dagger 2 module for creating the database layer of the project
 *
 * @Module - provides dependencies for the component
 * @Qualifier - marker on a class so that when it is encountered it is known which one to use,
 *              helpful when two things inherit from the same parent
 * @Retention - A tag that stays forever with the programming element even after compiled, and it is visible via reflection, in this case yes
 *
 * @Singleton - only one instance is shared among the application
 * @JvmStatic - creates an instance of the companion method for java classes to use it. that way it can be referenced the same
 *              way as a kotlin function
 * @PokemonLocalDataSource - is the result of the PokemonLocalDataSource annotation class
 * @Provides - used when there is no constructor or when the instantiate the dependency.
 *              provides means that the function provides the return data type. Modules typically contain
 *              several provides methods with them
 * @Binds - methods are just a method declaration, they are expressed as abstract methods
 */
@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class PokemonLocalDataSource

    @JvmStatic
    @Singleton
    @PokemonLocalDataSource
    @Provides
    fun providePokemonDataSource(database: PokemonDatabase, ioDispatcher: CoroutineDispatcher) : PokemonDataSource {
        return PokemonLocalDataSource(database.PokemonDao(), ioDispatcher)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDatabase(context: Context): PokemonDatabase {
        return Room.databaseBuilder(context.applicationContext, PokemonDatabase::class.java, "Pokemon.db")
            .addCallback(object: RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                    WorkManager.getInstance(context).enqueue(request)
                }
            })
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun providesIoDispatcher() = Dispatchers.IO
}

@Module
abstract class ApplicationModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repository: DefaultPokemonRepository): PokemonRepository
}