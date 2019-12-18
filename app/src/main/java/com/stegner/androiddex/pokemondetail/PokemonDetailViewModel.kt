package com.stegner.androiddex.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the details screen
 */
class PokemonDetailViewModel @Inject constructor(private val pokemonRepository: PokemonRepository): ViewModel() {

    // The pokemon that is being looked up
    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    // Flag to determine if data has been retrieved before
    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> = _isDataAvailable

    // Flag to determine if still retrieving data
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // Id of the pokemon from the pokedex
    private val pokedexId: Int?
        get() = _pokemon.value?.id

    /**
     * Loads pokemon from repository
     *
     * if found sets the mutable data
     *
     * if not found sets to null
     */
    fun start(pokedexId: Int?) {

        // if data has been loaded or currently loading
        if(_isDataAvailable.value == true || _dataLoading.value == true){
            return
        }

        _dataLoading.value = true

        viewModelScope.launch {
            if(pokedexId != null){
                pokemonRepository.getPokemon(pokedexId).let {result ->
                    if(result is Success){
                        onPokemonLoaded(result.data)
                    } else {
                        onPokemonNotLoaded(result)
                    }
                }
            }

            _dataLoading.value = false
        }

    }

    /**
     * Update mutable live values to null and false when no pokemon is found
     */
    private fun onPokemonNotLoaded(result: Result<Pokemon>){
        _pokemon.value = null
        _isDataAvailable.value = false
    }

    /**
     * Intermediate call to update mutable live values to loaded pokemon
     */
    private fun onPokemonLoaded(pokemon: Pokemon) {
        setPokemon(pokemon)
    }

    /**
     * Update mutable live values to the loaded pokemon
     */
    private fun setPokemon(pokemon: Pokemon?){
        this._pokemon.value = pokemon
        _isDataAvailable.value = pokemon != null
    }
}