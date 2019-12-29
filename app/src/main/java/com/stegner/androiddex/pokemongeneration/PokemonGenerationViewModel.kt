package com.stegner.androiddex.pokemongeneration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stegner.androiddex.util.Event
import javax.inject.Inject

/**
 * ViewModel for the Generation Select [PokemonGenerationFragment]
 */
class PokemonGenerationViewModel @Inject constructor() : ViewModel() {

    // Listener for when Generation is selected
    private val _openPokemonGenerationEvent = MutableLiveData<Event<Int>>()
    val openPokemonGenerationEvent: LiveData<Event<Int>> = _openPokemonGenerationEvent


    /**
     * Called by data binding for navigation
     */
    fun openPokemonList(generationId: Int){
        _openPokemonGenerationEvent.value = Event(generationId)
    }

}