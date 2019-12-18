package com.stegner.androiddex.pokemondetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stegner.androiddex.R
import dagger.android.support.DaggerFragment

/**
 * A simple [Fragment] subclass.
 */
class PokemonDetailFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pokemondetail_frag, container, false)
    }


}
