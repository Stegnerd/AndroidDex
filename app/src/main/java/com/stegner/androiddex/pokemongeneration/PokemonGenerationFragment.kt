package com.stegner.androiddex.pokemongeneration


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.stegner.androiddex.databinding.PokemongenerationFragBinding
import com.stegner.androiddex.util.EventObserver
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Landing page, when user picks generation of pokemon to lock at
 */
class PokemonGenerationFragment : DaggerFragment() {

    // Dagger injected viewmodel factory
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    // Viewmodel instantiated with lazy delegation of the factory
    private val viewModel by viewModels<PokemonGenerationViewModel> { viewModelFactory }

    // Instance of the view  from the fragment
    private lateinit var viewDataBinding: PokemongenerationFragBinding

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * bind the viewmodel values from the fragment to the view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = PokemongenerationFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }

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
    }

    /**
     * Called when the activity is starting
     *
     * This is used to hide the support bar on this fragment only
     */
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    /**
     * Called when the activity is stopping
     *
     * This un-hides the support bar for the rest of the fragments
     */
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    /**
     * Sets up navigation to the Pokemon List, then triggers navigation
     */
    private fun setupNavigation() {
        viewModel.openPokemonGenerationEvent.observe(this.viewLifecycleOwner, EventObserver {
            openPokemonList(it)
        })
    }

    /**
     * Triggers navigation to the List view, using fragment directions
     */
    private fun openPokemonList(genId: Int) {
        val action = PokemonGenerationFragmentDirections.actionPokemonGenerationFragmentToPokemonListFragment(genId)
        findNavController().navigate(action)
    }
}
