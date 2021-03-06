package com.stegner.androiddex.pokemonlist

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.stegner.androiddex.R
import com.stegner.androiddex.data.Result.Success
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.repository.PokemonRepository
import com.stegner.androiddex.data.succeeded
import com.stegner.androiddex.util.Event
import com.stegner.androiddex.util.GenerationFilterType
import com.stegner.androiddex.util.TypeFilter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the List View of pokemon [PokemonListFragment]
 */
class PokemonListViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) : ViewModel() {

    // just an instance of this classes name for logging purposes
    private val TAG by lazy { PokemonListViewModel::class.java.simpleName }

    // This is the list of items to display in the list view
    private val _items = MutableLiveData<List<Pokemon>>().apply { value = emptyList() }
    val items: LiveData<List<Pokemon>> = _items

    // Flag to determine if still retrieving data
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // Determines the id of the string representing the filter in the ui, ex: Kanto
    private val _currentGenerationFilteringLabel = MutableLiveData<Int>()
    val currentGenerationFilteringLabel: LiveData<Int> = _currentGenerationFilteringLabel

    // Determines the id of the string to show when no pokemon are available
    private val _noPokemonLabel = MutableLiveData<String>()
    val noPokemonLabel: LiveData<String> = _noPokemonLabel

    // Determines the id of the image to show when no pokemon are available
    private val _noPokemonIconRes = MutableLiveData<Int>()
    val noPokemonIconRes: LiveData<Int> = _noPokemonIconRes

    // Determines the id of the icon representing the filter in the ui, ex: [1]
    private val _filterIconRes = MutableLiveData<Int>()
    val filterIconRes: LiveData<Int> = _filterIconRes

    //pop up message test, like toast
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    // Listener for when a pokemon is selected
    private val _openPokemonEvent = MutableLiveData<Event<Int>>()
    val openPokemonEvent: LiveData<Event<Int>> = _openPokemonEvent

    // Determines the filter based on generation on the list of _items
    private var _curentGenerationFiltering = GenerationFilterType.ALL

    // Determines the filter based on type on the list of _items
    // set to MutableList to handle updates from ui
    private var _currentTypeFiltering = mutableListOf<TypeFilter>()

    // Determines the filter based on the name of the list of _items
    private var _currentNameFiltering = ""

    // used by the layout to determine when the list of items is empty
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    init {
        setGenerationFiltering(GenerationFilterType.ALL)
        setTypeFiltering(TypeFilter.All, true)
        loadPokemon()
    }

    private fun showSnackbarMessage(message: Int) {
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
    fun setGenerationFiltering(filter: GenerationFilterType) {
        _curentGenerationFiltering = filter

        when (filter) {
            GenerationFilterType.ALL -> {
                setGenerationFilter(R.string.label_all, R.drawable.ic_clear_filter_black_24dp)
            }
            GenerationFilterType.KANTO -> {
                setGenerationFilter(R.string.label_kanto, R.drawable.ic_filter_1_black_24dp)
            }
            GenerationFilterType.JHOTO -> {
                setGenerationFilter(R.string.label_jhoto, R.drawable.ic_filter_2_black_24dp)
            }
            GenerationFilterType.HOENN -> {
                setGenerationFilter(R.string.label_hoenn, R.drawable.ic_filter_3_black_24dp)
            }
            GenerationFilterType.SINNOH -> {
                setGenerationFilter(R.string.label_sinnoh, R.drawable.ic_filter_4_black_24dp)
            }
            GenerationFilterType.UNOVA -> {
                setGenerationFilter(R.string.label_unova, R.drawable.ic_filter_5_black_24dp)
            }
            GenerationFilterType.KALOS -> {
                setGenerationFilter(R.string.label_kalos, R.drawable.ic_filter_6_black_24dp)
            }
            GenerationFilterType.ALOLA -> {
                setGenerationFilter(R.string.label_alola, R.drawable.ic_filter_7_black_24dp)
            }
            GenerationFilterType.GALAR -> {
                setGenerationFilter(R.string.label_galar, R.drawable.ic_filter_8_black_24dp)
            }
        }
    }

    /**
     * Used to set mutable live data values for filters based on click events from the ui
     */
    private fun setGenerationFilter(@StringRes filteringLabelString: Int, @DrawableRes filteringIconDrawable: Int) {
        _currentGenerationFilteringLabel.value = filteringLabelString
        _filterIconRes.value = filteringIconDrawable
    }

    /**
     * Used to set the value of the name filter based on click events from the ui
     */
    fun setNameFiltering(nameFilter: String?){
        _currentNameFiltering = nameFilter ?: ""
    }

    /**
     * Updates the current type filter list.
     *
     * @param filter The [TypeFilter] to be added/removed
     * @param add Flag to determine if [TypeFilter] should be added
     */
    fun setTypeFiltering(filter: TypeFilter, add: Boolean) {
        if (add) {
            _currentTypeFiltering.add(filter)
        } else {
            _currentTypeFiltering.remove(filter)
        }

        // if clicked all to reset ui clear list and add All
        if (filter == TypeFilter.All) {
            _currentTypeFiltering.clear()
            _currentTypeFiltering.add(filter)
        }
        // If All is in the list and new type added remove All to apply filter
        // ex: All is checked and user clicks Electric
        else if (_currentTypeFiltering.contains(TypeFilter.All) && _currentTypeFiltering.count() > 1) {
            _currentTypeFiltering.remove(TypeFilter.All)
        }
        // If user unchecks all filters reset back to All
        // ex: Electric is checked and user unchecks it. Reset to All
        else if (_currentTypeFiltering.count() == 0) {
            _currentTypeFiltering.add(TypeFilter.All)
        }

        // Updates the filter string and icon when too many filters applied
        setTypeFilter()
    }

    /**
     * Sets the string and icon for when a filter has no results.
     */
    private fun setTypeFilter() {
        val currentFilterCommaString = _currentTypeFiltering.joinToString { it.toString() }

        _noPokemonLabel.value = "No pokemon of those type combinations exist: $currentFilterCommaString"
        _noPokemonIconRes.value = R.drawable.ic_gotcha
    }

    /**
     * Loads all pokemon from database into list view based on filtering
     */
    fun loadPokemon() {
        _dataLoading.value = true

        // This is the coroutine scope for the viewmodel async methods
        viewModelScope.launch {
            // all pokemon from  the repository
            val pokemonResult = pokemonRepository.getPokemon()

            if (pokemonResult is Success) {
                val pokemon = pokemonResult.data

                val nameFilteredList = nameFilter(pokemon)

                // filter all pokemon according to the generation
                val generationFilteredList = generationFilter(nameFilteredList)

                // filter the generation list based on the type filter
                val typeFilteredList = typeFilter(generationFilteredList)

                _items.value = ArrayList(typeFilteredList)
            } else {
                _items.value = emptyList()
                _dataLoading.value = false
                showSnackbarMessage(R.string.loading_pokemon_error)
            }

            _dataLoading.value = false
        }
    }

    /**
     * Iterates over list of loaded pokemon and applies generation filter to it.
     *
     * @return List<Pokemon> that is within filter
     */
    private fun generationFilter(prefilteredList: List<Pokemon>): List<Pokemon> {
        val filteredList = mutableListOf<Pokemon>()

        for (poke in prefilteredList) {
            if (_curentGenerationFiltering.generation == 0) {
                filteredList.add(poke)
            } else if (poke.generation == _curentGenerationFiltering.generation) {
                filteredList.add(poke)
            }
        }

        return filteredList
    }

    /**
     * Iterates over list of loaded pokemon and applies type filter to it.
     *
     * @return List<Pokemon> that is within filter
     */
    private fun typeFilter(prefilteredList: List<Pokemon>): List<Pokemon> {
        val filteredList = mutableListOf<Pokemon>()
        val typeListFilter = _currentTypeFiltering.map { it.toString() }

        for (poke in prefilteredList) {
            when {
                // if the filter type is all add it regardless
                _currentTypeFiltering.contains(TypeFilter.All) -> {
                    filteredList.add(poke)
                }
                // if there is no filter selected and user didn't hit All, add all
                // to simulate not filtered
                _currentTypeFiltering.count() == 0 -> {
                    filteredList.add(poke)
                }
                // if there has been filer selected make sure that it applies to all types or do
                // not add it to the list
                poke.types.containsAll(typeListFilter) -> {
                    filteredList.add(poke)
                }
            }
        }

        return filteredList
    }

    /**
     * Iterates over list of loaded pokemon and applies name filter to it.
     *
     * @return List<Pokemon> that is within filter
     */
    private fun nameFilter(listOfPokemon: List<Pokemon>): List<Pokemon>{

        if(_currentNameFiltering.isEmpty()){
            return listOfPokemon
        }

        val nameFilteredList = mutableListOf<Pokemon>()

        for (poke in listOfPokemon){
            if(poke.names.english.toLowerCase().contains(_currentNameFiltering.toLowerCase())){
                nameFilteredList.add(poke)
            }
        }

        return nameFilteredList
    }

    /**
     * Determines if the [TypeFilter] is already applied to the list.
     */
    fun isFilterApplied(type: TypeFilter): Boolean {
        return _currentTypeFiltering.contains(type)
    }

    /**
     * Called by data binding for navigation
     */
    fun openPokemonDetail(pokedexId: Int) {
        _openPokemonEvent.value = Event(pokedexId)
    }
}