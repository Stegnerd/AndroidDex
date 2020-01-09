package com.stegner.androiddex.data.pokemon.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.stegner.androiddex.MainCoroutineRule
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import com.stegner.androiddex.util.GenerationFilterType.*
import com.stegner.androiddex.util.TestSeedWorker
import com.stegner.androiddex.util.TypeFilter.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
 *
 * ExperimentalCoroutinesApi - Marks declarations that are still experimental in coroutines API,
 *                              which means that the design of the corresponding declarations has open
 *                              issues which may (or may not) lead to their changes in the future.
 *                              Roughly speaking, there is a chance that those declarations will be
 *                              deprecated in the near future or the semantics of their behavior may
 *                              change in some way that may break some code.
 *
 *   @get - kotlin getter access
 *   junit rule - Rules allow very flexible addition or redefinition of the behavior of each test
 *                method in a test class.
 *
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PokemonDaoTest {

    private lateinit var database: PokemonDatabase

    // switches coroutine scope to [TestCoroutineScope]. So we can more easily control the flow of
    // coroutines
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // makes asynchronous code run synchronously
    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Before running the test create the database and seed it
    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(getApplicationContext(), PokemonDatabase::class.java).allowMainThreadQueries().build()

        // seed the database
        val worker = TestSeedWorker(getApplicationContext(), database)
        runBlocking {
            worker.seed()
        }
    }

    // after all tests are done tear down the database
    @After
    fun closeDb() = database.close()

    @Test
    fun getPokemon_ReturnsListAll_WhenNoFilter() = runBlockingTest {
        // GIVEN - no filter provided

        // WHEN - Get all pokemon
        val pokemon = database.PokemonDao().getPokemon()

        // THEN - the loaded data contains the expected values
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon.count(), `is`(809))
    }

    @Test
    fun getPokemonById_ReturnsExpectedPokemon_WhenGivenId() = runBlockingTest {
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
    fun getPokemonByType_ReturnsPokemonListBug_WhenGivenFilterTypeBug() = runBlockingTest {
        // GIVEN - type filter is bug
        val type = Bug

        // WHEN - get all bug pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all bug pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(77))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListDark_WhenGivenFilterTypeDark() = runBlockingTest {
        // GIVEN - type filter is dark
        val type = Dark

        // WHEN - get all dark pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all dark pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(46))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListDragon_WhenGivenFilterTypeDragon() = runBlockingTest {
        // GIVEN - type filter is dragon
        val type = Dragon

        // WHEN - get all dragon pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all dragon pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(45))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListElectric_WhenGivenFilterTypeElectric() = runBlockingTest {
        // GIVEN - type filter is Electric
        val type = Electric

        // WHEN - get all Electric pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Electric pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(48))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListFairy_GivenFilterTypeFairy() = runBlockingTest {
        // GIVEN - type filter is Fairy
        val type = Fairy

        // WHEN - get all Fairy pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Fairy pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(47))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListFighting_GivenFilterTypeFighting() = runBlockingTest {
        // GIVEN - type filter is Fighting
        val type = Fighting

        // WHEN - get all Fighting pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Fighting pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(54))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListFire_GivenFilterTypeFire() = runBlockingTest {
        // GIVEN - type filter is Fire
        val type = Fire

        // WHEN - get all Fire pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Fire pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(64))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListFlying_GivenFilterTypeFlying() = runBlockingTest {
        // GIVEN - type filter is Flying
        val type = Flying

        // WHEN - get all Flying pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Flying pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(98))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListGhost_GivenFilterTypeGhost() = runBlockingTest {
        // GIVEN - type filter is Ghost
        val type = Ghost

        // WHEN - get all Ghost pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Ghost pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(43))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListGrass_GivenFilterTypeGrass() = runBlockingTest {
        // GIVEN - type filter is Grass
        val type = Grass

        // WHEN - get all Grass pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Grass pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(97))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListGround_GivenFilterTypeGround() = runBlockingTest {
        // GIVEN - type filter is Ground
        val type = Ground

        // WHEN - get all Ground pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Ground pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(64))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListIce_GivenFilterTypeIce() = runBlockingTest {
        // GIVEN - type filter is Ice
        val type = Ice

        // WHEN - get all Ice pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Ice pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(34))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListNormal_GivenFilterTypeNormal() = runBlockingTest {
        // GIVEN - type filter is Normal
        val type = Normal

        // WHEN - get all Normal pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Normal pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(109))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListPoison_GivenFilterTypePoison() = runBlockingTest {
        // GIVEN - type filter is Poison
        val type = Poison

        // WHEN - get all Poison pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Poison pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(66))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListPsychic_FilterTypePsychic() = runBlockingTest {
        // GIVEN - type filter is Psychic
        val type = Psychic

        // WHEN - get all Psychic pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Psychic pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(82))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListRock_GivenFilterTypeRock() = runBlockingTest {
        // GIVEN - type filter is Rock
        val type = Rock

        // WHEN - get all Rock pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Rock pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(60))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListSteel_GivenFilterTypeSteel() = runBlockingTest {
        // GIVEN - type filter is Steel
        val type = Steel

        // WHEN - get all Steel pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Steel pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(49))
    }

    @Test
    fun getPokemonByType_ReturnsPokemonListWater_GivenFilterTypeWater() = runBlockingTest {
        // GIVEN - type filter is Water
        val type = Water

        // WHEN - get all Water pokemon
        val pokemon = database.PokemonDao().getPokemonByType(type.toString())

        // THEN -- the loaded data contains all Water pokemon.
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(131))
    }

    @Test
    fun getPokemonByGeneration_ReturnsPokemonListGenerationKanto_GivenGenerationFilterKanto() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = KANTO

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(151))
    }

    @Test
    fun getPokemonByGeneration_ReturnsPokemonListGenerationJhoto_GivenGenerationFilterJhoto() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = JHOTO

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(100))
    }

    @Test
    fun getPokemonByGeneration_ReturnsPokemonListGenerationHoenn_GivenGenerationFilterHoenn() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = HOENN

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(135))
    }

    @Test
    fun getPokemonByGeneration_ReturnsPokemonListGenerationSinnoh_GivenGenerationFilterSinnoh() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = SINNOH

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(108))
    }

    @Test
    fun getPokemonByGeneration_ReturnsPokemonListGenerationUnova_GivenGenerationFilterUnova() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = UNOVA

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(155))
    }

    @Test
    fun getPokemonByGeneration_ReturnsPokemonListGenerationKalos_GivenGenerationFilterKalos() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = KALOS

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(72))
    }

    @Test
    fun getPokemonByGeneration_ReturnsPokemonListGenerationAlola_GivenGenerationFilterAlola() = runBlockingTest {
        // GIVEN - Generation filter is
        val gen = ALOLA

        // WHEN - get all   generation
        val pokemon = database.PokemonDao().getPokemonByGeneration(gen.generation)

        //Then - the loaded data contains all   generation pokemon
        assertThat<List<Pokemon>>(pokemon, notNullValue())
        assertThat(pokemon?.count(), `is`(88))
    }
}