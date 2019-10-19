package com.stegner.androiddex.data

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * Converters using for mapping database objects to model classes
 */
class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }

}