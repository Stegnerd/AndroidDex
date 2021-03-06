package com.stegner.androiddex.pokemonlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.children
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.stegner.androiddex.R
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.databinding.PokemonlistFragBinding
import com.stegner.androiddex.util.Constants.Settings.GRID_COLUMN_COUNT
import com.stegner.androiddex.util.EventObserver
import com.stegner.androiddex.util.GenerationFilterType
import com.stegner.androiddex.util.Helpers
import com.stegner.androiddex.util.ItemOffsetDecoration
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

/**
 * Display a grid of cards of [Pokemon]. Use can filter based on type
 */
class PokemonListFragment : DaggerFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PokemonListViewModel> {viewModelFactory}

    private val args: PokemonListFragmentArgs by navArgs()

    private lateinit var viewDataBinding: PokemonlistFragBinding

    private lateinit var listAdapter: PokemonListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        viewDataBinding = PokemonlistFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated
     *
     * This is called after onCreateView
     * @param savedInstanceState when the view is recreated, i.e. screen rotation is it's current values
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // since our viewmodel has livedata, we need to instantiate the lifecycle owner
        // if we don't it won't update
        // this is the PokemonListFragment.viewlifecycleowner
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner


        // bind the recycler view to the list adapter
        setUpListAdapter()
        // bind the recycler view to the grid adapter with custom padding
        setUpGridManager()
        // set up navigation with fragment directions
        setupNavigation()

        // Set the generation to the filter from nav args
        viewModel.setGenerationFiltering(GenerationFilterType.values()[args.genId])

        // Load pokemon from database with filters applied
        viewModel.loadPokemon()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pokemonlist_fragment_menu, menu)

        // Attach listeners to the search view
        setUpSearch(menu)
    }

    /**
     * Sets up listener for typing events with the search bar
     */
    private fun setUpSearch(menu: Menu){
        // get the item from the view
        val menuItem: MenuItem = menu.findItem(R.id.menu_search)
        // cast it to the proper type
        val searchView: SearchView = (menuItem.actionView as SearchView)

        // set the listeners on it
        searchView.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.setNameFiltering(query)
                viewModel.loadPokemon()
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.loadPokemon()
                return true
            }
        })

    }

    /**
     * Determines what happens after an option has been clicked from the option menu
     *
     * returns a boolean if an item from the menu was clicked
     * false if you clicked outside the menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_filter -> {
                showFilteringPopupMenu()
                true
            }
            else -> false
        }
    }

    /**
     * Displays the menu options for filtering and then triggers a load based on the option
     */
    private fun showFilteringPopupMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return

        // creates a popup, requirecontext is what it can have access to
        // view is the anchor that it attaches to
        PopupMenu(requireContext(), view).run {
            // menu is the menu from the view, since it was the anchor
            menuInflater.inflate(R.menu.filter_pokemon, menu)

            // we need to set the icon for each item in the menu since that needs to be set programmatically
            // Ex: if was checked before it will be in the list of the view model
            // that way when we use the click listener, we pass the opposite or false
            // https://stackoverflow.com/questions/6239163/android-checkable-menu-item
            menu.children.toList().forEach {
                it.isChecked = viewModel.isFilterApplied(Helpers.typeEnumFromId(it.itemId))
            }

            // set a click listener for each item in the menu
            setOnMenuItemClickListener {
                // helper method to get the enum value from the id clicked
                viewModel.setTypeFiltering(Helpers.typeEnumFromId(it.itemId), !it.isChecked)
                // reload data now that filter has been applied
                viewModel.loadPokemon()
                // reset to top of list when item from filter has been selected
                resetPositionToTop()

                // setOnMenuItemClickListener needs to return a bool to indicate that it is done handling the event
                true
            }
            // show the popup anchored to the view specified during construction
            show()
        }
    }

    /**
     * Sets up navigation to Pokemon Detail, then triggers navigation
     */
    private fun setupNavigation() {
        viewModel.openPokemonEvent.observe(this.viewLifecycleOwner, EventObserver {
            openPokemonDetails(it)
        })
    }

    /**
     * Binds the pokemon list recyclerview to the [PokemonListAdapter]
     */
    private fun setUpListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if(viewModel != null){
            listAdapter = PokemonListAdapter(viewModel)
            viewDataBinding.pokemonList.adapter = listAdapter
        }else {
            Timber.w("Viewmodel not initialized when attempting to set up adapter.")
        }
    }

    /**
     * Binds the pokemon list recyclerview to the [GridLayoutManager]
     *
     * Also adds custom decoration to add padding to each item in the list
     */
    private fun setUpGridManager(){
        val viewModel = viewDataBinding.viewmodel
        if(viewModel != null){
            // set the recycler view to use gridlayout, give it the context and how many columns there should be
            viewDataBinding.pokemonList.layoutManager = GridLayoutManager(requireContext(), GRID_COLUMN_COUNT)

            // add custom padding around each item in the list, in this case 8dp on all 4 sides
            viewDataBinding.pokemonList.addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen.gridlayout_item_offset))
        }else {
            Timber.w("Viewmodel is not initialized when attempting to set up GridManager.")
        }
    }

    /**
     * Updates the listadapter to the top position when filter has been changed
     */
    private fun resetPositionToTop() {
        val view = viewDataBinding.pokemonList
        if (view != null){

            // this only performs the action when the layout has been laid down
            view.doOnNextLayout {
                view.layoutManager?.scrollToPosition(0)
            }
        }else {
            Timber.w("viewmodel is not initialized when attempting to reset recycler position")
        }
    }

    /**
     * Triggers navigation to the Detail view, using fragment directions
     */
    private fun openPokemonDetails(pokedexId: Int) {
        val action = PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailFragment(pokedexId)
        findNavController().navigate(action)
    }
}