package com.stegner.androiddex.data.pokemon

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 *  Immutable model class for [Pokemon].
 *
 *  @param id    Pokedex number of the pokemon
 *  @param names Name of the pokemon in several languages
 *  @param types List of types that the pokemon is
 *  @param stats Base stats for the pokemon
 */
@Entity(tableName = "Pokemon")
data class Pokemon constructor(

    @PrimaryKey
    @ColumnInfo(name = "pokedex_number")
    val id: Int,

    @ColumnInfo(name = "generation")
    val generation: Int,

    @Embedded
    @SerializedName("name")
    val names: PokemonNames,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    val types: List<String>,

    @Embedded
    @SerializedName("base")
    val stats: PokemonStats
)

data class PokemonNames (
    @ColumnInfo(name = "english_name")
    val english: String,

    @ColumnInfo(name = "japanese_name")
    val japanese: String,

    @ColumnInfo(name = "chinese_name")
    val chinese: String,

    @ColumnInfo(name = "french_name")
    val french: String
)

data class PokemonStats(
    @SerializedName("HP")
    val hp: Int,

    @SerializedName("Attack")
    val attack: Int,

    @SerializedName("Defense")
    val defense: Int,

    @ColumnInfo(name = "sp_attack")
    @SerializedName("Sp. Attack")
    val sp_attack: Int,

    @ColumnInfo(name = "sp_defense")
    @SerializedName("Sp. Defense")
    val sp_defense: Int,

    @SerializedName("Speed")
    val speed: Int
)