package com.stegner.androiddex.pokemonlist

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stegner.androiddex.R
import com.stegner.androiddex.data.Result
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import com.stegner.androiddex.util.Event
import com.stegner.androiddex.util.GenerationFilterType
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonListViewModel @Inject constructor (private val pokemonRepository: PokemonRepository): ViewModel() {

    // This is the list of items to display in the list view
    private val _items= MutableLiveData<List<Pokemon>>().apply { value = emptyList() }
    val items: LiveData<List<Pokemon>> = _items

    // Flag to determine if still retrieving data
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // Determines the id of the string representing the filter in the ui, ex: Kanto
    private val _currentGenerationFilteringLabel = MutableLiveData<Int>()
    val currentGenerationFilteringLabel : LiveData<Int> = _currentGenerationFilteringLabel

    // Determines the id of the icon representing the filter in the ui, ex: [1]
    private val _filterIconRes = MutableLiveData<Int>()
    val filterIconRes : LiveData<Int> = _filterIconRes

    //pop up message test, like toast
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    // Determines the filter on the list of _items
    private var _cuurentGenerationFiltering = GenerationFilterType.ALL

    init {
        setFiltering(GenerationFilterType.ALL)
        loadPokemon()
    }

    private fun showSnackbarMessage(message: Int){
        _snackbarText.value = Event(message)
    }


    /**
     * Sets the current pokemon filtering type
     *
     * @param filter can be any of the following [GenerationFilterType.ALL],
     * [GenerationFilterType.KANTO], [GenerationFilterType.JHOTO], [GenerationFilterType.HOENN]
     * [GenerationFilterType.SINNOH], [GenerationFilterType.UNOVA], [GenerationFilterType.KALOS]
     * [GenerationFilterType.ALOLA], [GenerationFilterType.GALAR]
     */
    fun setFiltering(filter: GenerationFilterType){
        _cuurentGenerationFiltering = filter

        when(filter){
            GenerationFilterType.ALL -> {
                setFilter(R.string.label_all, R.drawable.ic_clear_filter_black_24dp)
            }
            GenerationFilterType.KANTO -> {
                setFilter(R.string.label_kanto, R.drawable.ic_filter_1_black_24dp)
            }
            GenerationFilterType.JHOTO -> {
                setFilter(R.string.label_jhoto, R.drawable.ic_filter_2_black_24dp)
            }
            GenerationFilterType.HOENN -> {
                setFilter(R.string.label_hoenn, R.drawable.ic_filter_3_black_24dp)
            }
            GenerationFilterType.SINNOH -> {
                setFilter(R.string.label_sinnoh, R.drawable.ic_filter_4_black_24dp)
            }
            GenerationFilterType.UNOVA -> {
                setFilter(R.string.label_unova, R.drawable.ic_filter_5_black_24dp)
            }
            GenerationFilterType.KALOS -> {
                setFilter(R.string.label_kalos, R.drawable.ic_filter_6_black_24dp)
            }
            GenerationFilterType.ALOLA -> {
                setFilter(R.string.label_alola, R.drawable.ic_filter_7_black_24dp)
            }
            GenerationFilterType.GALAR -> {
                setFilter(R.string.label_galar, R.drawable.ic_filter_8_black_24dp)
            }
        }
    }

    /**
     * Used to set mutable live data values for filters based on click events from the ui
     */
    private fun setFilter(@StringRes filteringLabelString: Int, @DrawableRes filteringIconDrawable: Int){
        _currentGenerationFilteringLabel.value = filteringLabelString
        _filterIconRes.value = filteringIconDrawable
    }

    /**
     * Loads all pokemon from database into list view based on filtering
     */
    fun loadPokemon() {

        // This is the coroutin scope for the viewmodel async methods
        viewModelScope.launch {
            // all pokemon from  the repository
            val pokemonResult = pokemonRepository.getPokemon()

            if(pokemonResult is Success){
                val pokemon = pokemonResult.data

                val pokemonToShow = ArrayList<Pokemon>()

                // filter all the pokemon based on the drop down filter from the ui
                for (poke in pokemon){
                    when(_cuurentGenerationFiltering){
                        GenerationFilterType.ALL -> pokemonToShow.add(poke)
                        GenerationFilterType.KANTO -> if(poke.generation == GenerationFilterType.KANTO.generation){
                            pokemonToShow.add(poke)
                        }
                        GenerationFilterType.JHOTO -> if(poke.generation == GenerationFilterType.JHOTO.generation){
                            pokemonToShow.add(poke)
                        }
                        GenerationFilterType.HOENN -> if(poke.generation == GenerationFilterType.HOENN.generation){
                            pokemonToShow.add(poke)
                        }
                        GenerationFilterType.SINNOH -> if(poke.generation == GenerationFilterType.SINNOH.generation){
                            pokemonToShow.add(poke)
                        }
                        GenerationFilterType.UNOVA -> if(poke.generation == GenerationFilterType.UNOVA.generation){
                            pokemonToShow.add(poke)
                        }
                        GenerationFilterType.KALOS -> if(poke.generation == GenerationFilterType.KALOS.generation){
                            pokemonToShow.add(poke)
                        }
                        GenerationFilterType.ALOLA -> if(poke.generation == GenerationFilterType.ALOLA.generation){
                            pokemonToShow.add(poke)
                        }
                        GenerationFilterType.GALAR -> if(poke.generation == GenerationFilterType.GALAR.generation){
                            pokemonToShow.add(poke)
                        }
                    }
                }

                _items.value = ArrayList(pokemonToShow)
            } else {
                _items.value = emptyList()
                showSnackbarMessage(R.string.loading_pokemon_error)
            }

            _dataLoading.value = false
        }
    }
}