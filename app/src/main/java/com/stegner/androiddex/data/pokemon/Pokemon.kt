package com.stegner.androiddex.data.pokemon

import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlin.properties.Delegates

/**
 *  Immutable model class for [Pokemon]. Top level model for view
 *
 *  @param id    Pokedex number of the pokemon.
 *  @param names Name of the pokemon in several languages. [PokemonNames]
 *  @param types List of types that the pokemon is.
 *  @param stats Base stats for the pokemon. [PokemonStats]
 *
 *  @property images resourceIds for icon and thumbnail. [ImageInfo]
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
) {

    // Ignore persistence of this to the database
    @Ignore
    var images: ImageInfo  = ImageInfo()
}

/**
 * Container for [Pokemon] name in four languages
 *
 * @param english Name in English
 * @param japanese Name in Japanese
 * @param chinese Name in Chinese
 * @param french Name in French
 */
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

/**
 * Container for pokemon stats
 *
 * @param hp Hit points for the pokemon.
 * @param attack Attack stat of the pokemon.
 * @param defense Defense stat of the pokemon.
 * @param sp_attack Special attack stat of the pokemon.
 * @param sp_defense Special defense stat of the pokemon.
 * @param speed Speed stat of the pokemon.
 */
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

/**
 * Container for ResourceId of icon and thumbnail images
 *
 * Using delegates to assure that this is an int and it will not be null when it is being used.
 * this is an example of lazy delegation
 * https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.properties/-delegates/not-null.html
 */
class ImageInfo{
    /**
     * ResourceId of of sprite icon in drawable
     */
    var spriteId by Delegates.notNull<Int>()

    /**
     * ResourceId of of thumbnail icon in drawable
     */
    var thumbnailId by Delegates.notNull<Int>()
}