package com.stegner.androiddex.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // First type that the pokemon belongs to
    private val _typeOne = MutableLiveData<String>()
    val typeOne: LiveData<String> = _typeOne

    // Second type that the pokemon belongs to, if mono type it is empty string
    private val _typeTwo = MutableLiveData<String>()
    val typeTwo: LiveData<String> = _typeTwo

    // Percentage of stat based on 255 max
    private val _hpValue = MutableLiveData<Int>()
    val hpValue : LiveData<Int> = _hpValue

    // Percentage of stat based on 255 max
    private val _attackValue = MutableLiveData<Int>()
    val attackValue : LiveData<Int> = _attackValue

    // Percentage of stat based on 255 max
    private val _defenseValue = MutableLiveData<Int>()
    val defenseValue: LiveData<Int> = _defenseValue

    // Percentage of stat based on 255 max
    private val _specialAttackValue = MutableLiveData<Int>()
    val specialAttackValue : LiveData<Int> = _specialAttackValue

    // Percentage of stat based on 255 max
    private val _specialDefenseValue = MutableLiveData<Int>()
    val specialDefenseValue : LiveData<Int> = _specialDefenseValue

    // Percentage of stat based on 255 max
    private val _speedValue = MutableLiveData<Int>()
    val speedValue: LiveData<Int> = _speedValue

    // Resource id of the image
    private val _thumbnailIconRes = MutableLiveData<Int>()
    val thumbnailIconRes: LiveData<Int> = _thumbnailIconRes

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
                        onPokemonNotLoaded()
                    }
                }
            }

            _dataLoading.value = false
        }

    }

    /**
     * Update mutable live values to null and false when no pokemon is found
     */
    private fun onPokemonNotLoaded(){
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
    private fun setPokemon(pokemon: Pokemon){

        _pokemon.value = pokemon

        // set the types to display in the ui
        // if not dual typing, set it to empty string for ease of displaying
        _typeOne.value = pokemon.types[0]
        _typeTwo.value = if (pokemon.types.count() > 1) pokemon.types[1] else ""

        // Set the stats of the progress bar
        _hpValue.value = pokemon.stats.hp
        _attackValue.value = pokemon.stats.attack
        _defenseValue.value = pokemon.stats.defense
        _specialAttackValue.value = pokemon.stats.sp_attack
        _specialDefenseValue.value = pokemon.stats.sp_defense
        _speedValue.value = pokemon.stats.speed

        _isDataAvailable.value = true
    }

    /**
     * Sets the resource id of the image to be displayed
     */
    fun setThumbnailIcon(resId: Int){
        _thumbnailIconRes.value = resId
    }
}