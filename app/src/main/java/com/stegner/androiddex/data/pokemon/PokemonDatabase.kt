package com.stegner.androiddex.data.pokemon

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stegner.androiddex.data.Converters

/**
 * Pokemon database for the app
 *
 * The converters class is used to convert the list json of types into a list of string
 * for the [Pokemon] data model
 */
@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class  PokemonDatabase: RoomDatabase() {
    abstract fun PokemonDao(): PokemonDao
}