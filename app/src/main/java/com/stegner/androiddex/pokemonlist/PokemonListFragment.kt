package com.stegner.androiddex.pokemonlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.stegner.androiddex.R
import com.stegner.androiddex.databinding.PokemonlistFragBinding
import com.stegner.androiddex.util.Helpers
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class PokemonListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PokemonListViewModel> {viewModelFactory}

    //private val args: PokemonListFragmentArgs

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

        // set up navigation with fragment directions
        setupNavigation()

        // bind the recycler view to the list adapter
        setUpListAdapter()

        viewModel.loadPokemon()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pokemonlist_fragment_menu, menu)
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

            // set a click listener for each item in the menu
            setOnMenuItemClickListener {
                // helper method to get the enum value from the id clicked
                viewModel.setTypeFiltering(Helpers.typeEnumFromId(it.itemId))
                // reload data now that filter has been applied
                viewModel.loadPokemon()
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
        // TODO need to implement this when have fragments
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
}