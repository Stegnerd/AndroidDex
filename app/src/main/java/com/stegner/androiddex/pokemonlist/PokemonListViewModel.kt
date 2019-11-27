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

class PokemonListViewModel @Inject constructor (private val pokemonRepository: PokemonRepository): ViewModel() {

    // just an instance of this classes name for logging purposes
    private val TAG by lazy { PokemonListViewModel::class.java.simpleName}

    // This is the list of items to display in the list view
    private val _items= MutableLiveData<List<Pokemon>>().apply { value = emptyList() }
    val items: LiveData<List<Pokemon>> = _items

    // Flag to determine if still retrieving data
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // Determines the id of the string representing the filter in the ui, ex: Kanto
    private val _currentGenerationFilteringLabel = MutableLiveData<Int>()
    val currentGenerationFilteringLabel : LiveData<Int> = _currentGenerationFilteringLabel

    private val _currentTypeFilteringLabel = MutableLiveData<Int>()
    val currentTypeFilteringLabel : LiveData<Int> = _currentTypeFilteringLabel

    // Determines the id of the string to show when no pokemon are available
    private val _noPokemonLabel= MutableLiveData<Int>()
    val noPokemonLabel: LiveData<Int> = _noPokemonLabel

    // Determines the id of the image to show when no pokemon are available
    private val _noPokemonIconRes = MutableLiveData<Int>()
    val noPokemonIconRes: LiveData<Int> = _noPokemonIconRes

    // Determines the id of the icon representing the filter in the ui, ex: [1]
    private val _filterIconRes = MutableLiveData<Int>()
    val filterIconRes : LiveData<Int> = _filterIconRes

    //pop up message test, like toast
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    // Determines the filter based on generation on the list of _items
    private var _curentGenerationFiltering = GenerationFilterType.ALL

    // Determines the filter based on type on the list of _items
    private var _currentTypeFiltering = TypeFilter.All

    // used by the layout to determine when the list of items is empty
    val empty: LiveData<Boolean> = Transformations.map(_items){
        it.isEmpty()
    }

    init {
        setGenerationFiltering(GenerationFilterType.ALL)
        setTypeFiltering(TypeFilter.All)
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
    fun setGenerationFiltering(filter: GenerationFilterType){
        _curentGenerationFiltering = filter

        when(filter){
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
    private fun setGenerationFilter(@StringRes filteringLabelString: Int, @DrawableRes filteringIconDrawable: Int){
        _currentGenerationFilteringLabel.value = filteringLabelString
        _filterIconRes.value = filteringIconDrawable
    }

    fun setTypeFiltering(filter: TypeFilter){
        _currentTypeFiltering = filter

        when(filter){
            TypeFilter.All -> {
                setTypeFilter(R.string.label_all)
            }
            TypeFilter.Bug -> {
                setTypeFilter(R.string.label_type_bug)
            }
            TypeFilter.Dark -> {
                setTypeFilter(R.string.label_type_dark)
            }
            TypeFilter.Dragon -> {
                setTypeFilter(R.string.label_type_dragon)
            }
            TypeFilter.Electric -> {
                setTypeFilter(R.string.label_type_electric)
            }
            TypeFilter.Fairy -> {
                setTypeFilter(R.string.label_type_fairy)
            }
            TypeFilter.Fighting -> {
                setTypeFilter(R.string.label_type_fighting)
            }
            TypeFilter.Fire -> {
                setTypeFilter(R.string.label_type_fire)
            }
            TypeFilter.Flying -> {
                setTypeFilter(R.string.label_type_flying)
            }
            TypeFilter.Ghost -> {
                setTypeFilter(R.string.label_type_ghost)
            }
            TypeFilter.Grass -> {
                setTypeFilter(R.string.label_type_grass)
            }
            TypeFilter.Ground -> {
                setTypeFilter(R.string.label_type_ground)
            }
            TypeFilter.Ice -> {
                setTypeFilter(R.string.label_type_ice)
            }
            TypeFilter.Normal -> {
                setTypeFilter(R.string.label_type_normal)
            }
            TypeFilter.Poison -> {
                setTypeFilter(R.string.label_type_poison)
            }
            TypeFilter.Psychic -> {
                setTypeFilter(R.string.label_type_psychic)
            }
            TypeFilter.Rock -> {
                setTypeFilter(R.string.label_type_rock)
            }
            TypeFilter.Steel -> {
                setTypeFilter(R.string.label_type_steel)
            }
            TypeFilter.Water -> {
                setTypeFilter(R.string.label_type_water)
            }
        }
    }

    private fun setTypeFilter(@StringRes filteringLabelString: Int){
        _currentTypeFilteringLabel.value = filteringLabelString
        _noPokemonLabel.value = R.string.label_all_no_pokemon
        _noPokemonIconRes.value = R.drawable.ic_gotcha
    }

    /**
     * Loads all pokemon from database into list view based on filtering
     */
    fun loadPokemon() {

        // This is the coroutine scope for the viewmodel async methods
        viewModelScope.launch {
            // all pokemon from  the repository
            val pokemonResult = pokemonRepository.getPokemon()

            Log.w(TAG, "pokemonResult: ${pokemonResult.succeeded}")

            if(pokemonResult is Success){
                val pokemon = pokemonResult.data

                Log.w(TAG, "pokemon count: ${pokemon.count()}")
                Log.w(TAG, "Generation filter: ${_curentGenerationFiltering.generation}")
                Log.w(TAG, "type filter: ${_currentTypeFiltering}")

                // filter all pokemon according to the generation
                val generationFilteredList = generationFilter(pokemon)

                Log.w(TAG, "Generation filtered list count: ${generationFilteredList.count()}")

                // filter the generation list based on the type filter
                val typeFilteredList = typeFilter(generationFilteredList)

                Log.w(TAG, "Type filtered list count: ${typeFilteredList.count()}")

                _items.value = ArrayList(typeFilteredList)
            } else {
                _items.value = emptyList()
                showSnackbarMessage(R.string.loading_pokemon_error)
            }

            _dataLoading.value = false
        }
    }

    private fun generationFilter(prefilteredList: List<Pokemon>): List<Pokemon> {
        val filteredList = mutableListOf<Pokemon>()

        for (poke in prefilteredList){
            if(_curentGenerationFiltering.generation == 0){
                filteredList.add(poke)
            }else if(poke.generation == _curentGenerationFiltering.generation) {
                filteredList.add(poke)
            }
        }

        return filteredList
    }

    private fun typeFilter(prefilteredList: List<Pokemon>): List<Pokemon> {
        val filteredList = mutableListOf<Pokemon>()

        for (poke in prefilteredList) {
            if (_currentTypeFiltering.type == 0) {
                filteredList.add(poke)
            } else if (poke.types.contains(_currentTypeFiltering.toString())) {
                filteredList.add(poke)
            }
        }

        return filteredList
    }
}