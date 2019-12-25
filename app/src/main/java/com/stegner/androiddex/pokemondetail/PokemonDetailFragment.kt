package com.stegner.androiddex.pokemondetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.stegner.androiddex.R
import com.stegner.androiddex.databinding.PokemondetailFragBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PokemonDetailFragment : DaggerFragment() {

    private lateinit var  viewDataBinding: PokemondetailFragBinding

    private val args: PokemonDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PokemonDetailViewModel> { viewModelFactory}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pokemondetail_frag, container, false)
        viewDataBinding = PokemondetailFragBinding.bind(view).apply {
            viewmodel = viewModel
        }

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.start(args.pokedexId)

        bindThumbnail()

        return view
    }

    /**
     * Generates resource id for the icon, and updates the view model
     */
    private fun bindThumbnail(){
        val paddedNumber = args.pokedexId.toString().padStart(3,'0')
        val thumbnailIdentifier = viewDataBinding.root.context.resources.getIdentifier("thumbnail_${paddedNumber}", "drawable", viewDataBinding.root.context.packageName)

        viewModel.setThumbnailIcon(thumbnailIdentifier)
    }
}
