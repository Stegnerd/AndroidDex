package com.stegner.androiddex.pokemongeneration


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stegner.androiddex.R

/**
 * A simple [Fragment] subclass.
 */
class PokemonGenerationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pokemongeneration_fragment, container, false)
    }


}
