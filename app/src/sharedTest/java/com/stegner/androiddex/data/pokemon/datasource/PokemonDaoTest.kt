package com.stegner.androiddex.data.pokemon.datasource

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import com.stegner.androiddex.dependencyinjection.workers.DataBaseSeedWorker
import com.stegner.androiddex.util.TypeFilter.*
import com.stegner.androiddex.util.GenerationFilterType.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit tests of the dao
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PokemonDaoTest {

    private lateinit var database: PokemonDatabase

    /*
    @get:Rule
    var instantExecutorRule*/

    @Before
    fun initDatabase() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database =
            Room.inMemoryDatabaseBuilder(getApplicationContext(), PokemonDatabase::class.java)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<DataBaseSeedWorker>().build()
                        WorkManager.getInstance(getApplicationContext()).enqueue(request)
                    }
                })
                .allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun getPokemonListAll() = runBlockingTest {
        // GIVEN - no filter provided

        // WHEN - Get all pokemon
        val pokemon = database.PokemonDao().getPokemon()

        // THEN - the loaded data contains the expected values
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon.count(), `is`(809))
    }

    @Test
    fun getPokemonById() = runBlockingTest {
        // GIVEN - pokedex lookup id
        val pokedexId = 135

        // WHEN - Get specific pokemon by id
        val pokemon = database.PokemonDao().getPokemonById(pokedexId)

        // THEN - the loaded data contains the expected values
        assertThat<Pokemon>(pokemon, notNullValue())
        assertThat(pokemon?.id, `is`(135))
        assertThat(pokemon?.names?.english, `is`("Jolteon"))
        assertThat(pokemon?.types, hasItem("Electric"))
    }


    @Test
    fun getPokemonListBug() = runBlockingTest {
        // GIVEN - type filter is bug
        val type = Bug

        // WHEN - get all bug pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all bug pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(77))
    }

    @Test
    fun getPokemonListDark() = runBlockingTest {
        // GIVEN - type filter is dark
        val type = Dark

        // WHEN - get all dark pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all dark pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(56))
    }

    @Test
    fun getPokemonListDragon() = runBlockingTest {
        // GIVEN - type filter is dragon
        val type = Dragon

        // WHEN - get all dragon pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all dragon pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(44))
    }

    @Test
    fun getPokemonListElectric() = runBlockingTest {
        // GIVEN - type filter is Electric
        val type = Electric

        // WHEN - get all Electric pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Electric pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(52))
    }

    @Test
    fun getPokemonListFairy() = runBlockingTest {
        // GIVEN - type filter is Fairy
        val type = Fairy

        // WHEN - get all Fairy pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Fairy pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(51))
    }

    @Test
    fun getPokemonListFighting() = runBlockingTest {
        // GIVEN - type filter is Fighting
        val type = Fighting

        // WHEN - get all Fighting pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Fighting pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(57))
    }

    @Test
    fun getPokemonListFire() = runBlockingTest {
        // GIVEN - type filter is Fire
        val type = Fire

        // WHEN - get all Fire pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Fire pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(67))
    }

    @Test
    fun getPokemonListFlying() = runBlockingTest {
        // GIVEN - type filter is Flying
        val type = Flying

        // WHEN - get all Flying pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Flying pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(104))
    }

    @Test
    fun getPokemonListGhost() = runBlockingTest {
        // GIVEN - type filter is Ghost
        val type = Ghost

        // WHEN - get all Ghost pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Ghost pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(44))
    }

    @Test
    fun getPokemonListGrass() = runBlockingTest {
        // GIVEN - type filter is Grass
        val type = Grass

        // WHEN - get all Grass pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Grass pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(98))
    }

    @Test
    fun getPokemonListGround() = runBlockingTest {
        // GIVEN - type filter is Ground
        val type = Ground

        // WHEN - get all Ground pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Ground pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(66))
    }

    @Test
    fun getPokemonListIce() = runBlockingTest {
        // GIVEN - type filter is Ice
        val type = Ice

        // WHEN - get all Ice pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Ice pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(43))
    }

    @Test
    fun getPokemonListNormal() = runBlockingTest {
        // GIVEN - type filter is Normal
        val type = Normal

        // WHEN - get all Normal pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Normal pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(117))
    }

    @Test
    fun getPokemonListPoison() = runBlockingTest {
        // GIVEN - type filter is Poison
        val type = Poison

        // WHEN - get all Poison pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Poison pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(64))
    }

    @Test
    fun getPokemonListPsychic() = runBlockingTest {
        // GIVEN - type filter is Psychic
        val type = Psychic

        // WHEN - get all Psychic pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Psychic pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(88))
    }

    @Test
    fun getPokemonListRock() = runBlockingTest {
        // GIVEN - type filter is Rock
        val type = Rock

        // WHEN - get all Rock pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Rock pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(59))
    }

    @Test
    fun getPokemonListSteel() = runBlockingTest {
        // GIVEN - type filter is Steel
        val type = Steel

        // WHEN - get all Steel pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Steel pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(57))
    }

    @Test
    fun getPokemonListWater() = runBlockingTest {
        // GIVEN - type filter is Water
        val type = Water

        // WHEN - get all Water pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Water pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(133))
    }

    @Test
    fun getPokemonListGenerationKanto() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = KANTO

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(151))
    }

    @Test
    fun getPokemonListGenerationJhoto() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = JHOTO

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(100))
    }

    @Test
    fun getPokemonListGenerationHoenn() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = HOENN

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(135))
    }

    @Test
    fun getPokemonListGenerationSinnoh() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = SINNOH

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(107))
    }

    @Test
    fun getPokemonListGenerationUnova() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = UNOVA

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(156))
    }

    @Test
    fun getPokemonListGenerationKalos() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = KALOS

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(72))
    }

    @Test
    fun getPokemonListGenerationAlola() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = ALOLA

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(88))
    }
}