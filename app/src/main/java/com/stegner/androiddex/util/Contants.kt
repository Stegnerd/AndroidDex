package com.stegner.androiddex.util

/**
 * Constants used throughout the app
 */

// File Names
const val POKEMON_FILE_NAME = "pokedex.json"


// Errors
// Database
const val GET_POKEMON_LIST_ERROR = "ERROR: fetching pokemon list from database" // When attempting to get list of pokemon from database
const val GET_POKEMON_ERROR = "ERROR: fetching pokemon from database, ID: " // When attempting to get a specific pokemon from database
const val GET_POKEMON_BY_TYPE_ERROR = "ERROR: fetching pokemon list of specific type from database, Type(s): " // When attempting to get a list of pokemon of a specific type
const val GET_POKEMON_BY_GENERATION_ERROR = "ERROR: fetching pokemon list of specific generation: "