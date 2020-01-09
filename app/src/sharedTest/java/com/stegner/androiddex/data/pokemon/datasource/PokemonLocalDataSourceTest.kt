package com.stegner.androiddex.data.pokemon.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.stegner.androiddex.MainCoroutineRule
import com.stegner.androiddex.data.pokemon.PokemonDatabase
import com.stegner.androiddex.util.TestSeedWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration test for [PokemonLocalDataSource]
 *
 * None needed yet, only gets at this moment.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class PokemonLocalDataSourceTest{

    private lateinit var localDataSource: PokemonLocalDataSource
    private lateinit var database: PokemonDatabase

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        database = Room
            .inMemoryDatabaseBuilder(getApplicationContext(), PokemonDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        localDataSource = PokemonLocalDataSource(database.PokemonDao(), Dispatchers.Main)


        val worker = TestSeedWorker(getApplicationContext(), database)
        runBlocking {
            worker.seed()
        }
    }

    @After
    fun cleanUp(){
        database.close()
    }

    @Test
    fun placeholderTest() {
        assertThat(1, IsEqual(1))
    }
}